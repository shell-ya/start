package com.starnft.star.interfaces.controller.trans;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.common.ResultCode;
import com.starnft.star.common.chain.TiChainFactory;
import com.starnft.star.common.chain.model.req.ChainUserInfoReq;
import com.starnft.star.common.chain.model.req.CreateAccountReq;
import com.starnft.star.common.chain.model.res.ChainUserInfoRes;
import com.starnft.star.common.chain.model.res.CreateAccountRes;
import com.starnft.star.common.page.RequestPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.notify.model.req.NotifyOrderReq;
import com.starnft.star.domain.notify.service.NotifyOrderService;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.model.vo.WalletVO;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.interfaces.aop.BusinessTypeEnum;
import com.starnft.star.interfaces.aop.Log;
import com.starnft.star.interfaces.controller.trans.bo.C2BTransNotifyBO;
import com.starnft.star.interfaces.controller.trans.bo.C2CTransNotifyBO;
import com.starnft.star.interfaces.controller.trans.bo.SandCashierPayNotifyBO;
import com.starnft.star.interfaces.controller.trans.redis.RedisDistributedLock;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/dataHandle")
@Api(tags = "数据处理｜DataHandleController")
@Slf4j
public class DataHandleController {

    @Autowired
    private UserCore userCore;

    @Resource
    TiChainFactory tiChainServer;

    @Autowired
    WalletService walletService;

    @Autowired
    INumberService numberService;

    @Autowired
    private SpringAsyncConfig asyncConfig;


    @TokenIgnore
    @ApiOperation("用户链上地址更新")
    @GetMapping(path = "updateUserChain")
    public String updateUserChain() {
        asyncConfig.asyncExecutor().submit(this::updateUserChainHandle);
        return "用户链上地址更新中.....";
    }

    @TokenIgnore
    @ApiOperation("藏品转移")
    @GetMapping(path = "nftTransfer")
    public String nftTransfer() {
        asyncConfig.asyncExecutor().submit(()->numberService.transfer());
        return "藏品转移数据处理中.....";
    }

    @TokenIgnore
    @ApiOperation("重新发布")
    @GetMapping(path = "rePublishNFT")
    public String rePublishNFT(@RequestParam("type") Integer type) {
        asyncConfig.asyncExecutor().submit(() -> numberService.rePublishNFT(type));
        return "重新发布数据处理中.....";
    }

    /**
     * 异步处理数据
     */
    public void updateUserChainHandle() {
        try {

            List<WalletVO> walletVOS = walletService.selectAllWallet2();

            walletVOS.stream().parallel().forEach(wallet -> {

                // 如果天河链上地址为空，则更新
                if (StrUtil.isBlank(wallet.getThWId())) {
                    CreateAccountReq createAccountReq = new CreateAccountReq();
                    createAccountReq.setUserId(String.valueOf(wallet.getUid()));
                    String userKey = SecureUtil.sha1(String.valueOf(wallet.getUid()).concat("lywc"));
                    createAccountReq.setUserKey(userKey);

                    CreateAccountRes account = tiChainServer.createAccount(createAccountReq);
                    log.info("account:{}", JSONUtil.toJsonStr(account));
                    String address;
                    if (account.getCode().equals(802000)) {
                        ChainUserInfoReq req = new ChainUserInfoReq();
                        req.setUserId(String.valueOf(wallet.getUid()));
                        req.setUserKey(userKey);
                        ChainUserInfoRes chainUserInfoRes = tiChainServer.userInfo(req);
                        address = chainUserInfoRes.getData().getAddress();
                    } else {
                        address = account.getData().getPubKey().getAddress();
                    }
                    if (Objects.isNull(address)) {
                        log.error("address is null............");
                        return;
                    }
                    // 更新用户链上地址
                    walletService.updateUserThWId(wallet.getUid(), address);
                }
            });

        } catch (Exception e) {
            log.error("更新用户天河链钱包地址出错,错误信息:{}", e.getMessage(), e);
        }
    }

}
