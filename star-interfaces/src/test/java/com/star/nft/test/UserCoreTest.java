package com.star.nft.test;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.common.chain.TiChainFactory;
import com.starnft.star.common.chain.config.ChainConfiguration;
import com.starnft.star.common.chain.model.req.ChainUserInfoReq;
import com.starnft.star.common.chain.model.req.CreateAccountReq;
import com.starnft.star.common.chain.model.res.ChainUserInfoRes;
import com.starnft.star.common.chain.model.res.CreateAccountRes;
import com.starnft.star.common.utils.JsonUtil;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.service.WalletService;
import com.starnft.star.interfaces.StarApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest(classes = {StarApplication.class})
public class UserCoreTest {

    @Autowired
    private UserCore userCore;

    @Resource
    TiChainFactory tiChainServer;

    @Resource
    ChainConfiguration chainConfiguration;

    @Autowired
    WalletService walletService;

    @Test
    public void usercreate() {
        CreateAccountReq createAccountReq = new CreateAccountReq();
        createAccountReq.setUserId("977431137");
        String userKey = SecureUtil.sha1("977431137".concat("lywc"));
        createAccountReq.setUserKey(userKey);
        System.out.println(userKey);
        CreateAccountRes account = tiChainServer.createAccount(createAccountReq);
        System.out.println(account);
    }

    @Test
    public void test() {
        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.setLoginScenes(2);
        userLoginReq.setPhone(RandomUtil.randomPhone());
        userLoginReq.setPassword("123456");
        userLoginReq.setSc("BQQMCXKV");
        userCore.loginByPhoneAndRegister(userLoginReq);
    }

    @Test
    public void getAllUser() {
        try {
            List<UserInfo> allUser = userCore.getAllUser();
            System.out.println(allUser.size());
            allUser.forEach(userInfo -> {
                // 查询钱包
                WalletResult walletResult = walletService.queryWalletInfo(new WalletInfoReq(userInfo.getAccount()));
                log.info("wallet:{}", JSONUtil.toJsonStr(walletResult));

                if (StrUtil.isNotBlank(walletResult.getThWId())) {
                    log.info("用户天河链上地址不为空，跳过处理");
                    return;
                }
                // 如果天河链上地址为空，则更新
                if (StrUtil.isEmpty(walletResult.getThWId())) {
                    CreateAccountReq createAccountReq = new CreateAccountReq();
                    createAccountReq.setUserId(String.valueOf(userInfo.getAccount()));
                    String userKey = SecureUtil.sha1(String.valueOf(userInfo.getAccount()).concat("lywc"));
                    createAccountReq.setUserKey(userKey);
                    System.out.println(userKey);
                    CreateAccountRes account = tiChainServer.createAccount(createAccountReq);
                    log.info("account:{}", JSONUtil.toJsonStr(account));
                    String address;
                    if (account.getCode().equals(802000)) {
                        ChainUserInfoReq req = new ChainUserInfoReq();
                        req.setUserId(String.valueOf(userInfo.getAccount()));
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
                    walletService.updateUserThWId(walletResult.getUid(), address);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
