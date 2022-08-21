package com.starnft.star.common.chain;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.starnft.star.common.chain.config.ChainConfiguration;
import com.starnft.star.common.chain.constants.ChainConstants;
import com.starnft.star.common.chain.model.req.*;
import com.starnft.star.common.chain.model.res.*;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TiChainFactory {
    @Resource
    ChainConfiguration chainConfiguration;

    /**
     * 创建用户
     *
     * @param createAccountReq
     * @return
     */
    public CreateAccountRes createAccount(CreateAccountReq createAccountReq) {
        Assert.notNull(createAccountReq.getUserId(), () -> new StarException("userId不能为空"));
        Assert.notNull(createAccountReq.getUserKey(), () -> new StarException("UserKey不能为空"));
        return JSONUtil.toBean(request(createAccountReq, ChainConstants.create_account), CreateAccountRes.class);
    }

    /**
     * 发行商品
     *
     * @param publishGoodsReq
     * @return
     */
    public PublishGoodsRes publishGoods(PublishGoodsReq publishGoodsReq) {
        Assert.notNull(publishGoodsReq.getUserId(), () -> new StarException("userId不能为空"));
        Assert.notNull(publishGoodsReq.getUserKey(), () -> new StarException("UserKey不能为空"));
        Assert.notNull(publishGoodsReq.getName(), () -> new StarException("name不能为空"));
        Assert.notNull(publishGoodsReq.getPieceCount(), () -> new StarException("PieceCount不能为空"));
        Assert.notNull(publishGoodsReq.getAuthor(), () -> new StarException("Author不能为空"));
        return JSONUtil.toBean(request(publishGoodsReq, ChainConstants.publish_goods), PublishGoodsRes.class);
    }

    /**
     * 发起交易
     *
     * @param goodsTransferReq
     * @return
     */
    public GoodsTransferRes goodsTransfer(GoodsTransferReq goodsTransferReq) {
        Assert.notNull(goodsTransferReq.getUserId(), () -> new StarException("userId不能为空"));
        Assert.notNull(goodsTransferReq.getUserKey(), () -> new StarException("UserKey不能为空"));
        Assert.notNull(goodsTransferReq.getContractAddress(), () -> new StarException("ContractAddress不能为空"));
        Assert.notNull(goodsTransferReq.getTokenId(), () -> new StarException("TokenId不能为空"));
        Assert.notNull(goodsTransferReq.getTo(), () -> new StarException("to不能为空"));
        Assert.notNull(goodsTransferReq.getFrom(), () -> new StarException("from不能为空"));
        return JSONUtil.toBean(request(goodsTransferReq, ChainConstants.goods_transfer), GoodsTransferRes.class);
    }

    /**
     * h查询交易详情
     *
     * @param transactionDetailReq
     * @return
     */
    public TransactionDetailRes transferDetail(TransactionDetailReq transactionDetailReq) {
        Assert.notNull(transactionDetailReq.getUserId(), () -> new StarException("userId不能为空"));
        Assert.notNull(transactionDetailReq.getUserKey(), () -> new StarException("UserKey不能为空"));
        Assert.notNull(transactionDetailReq.getMethodName(), () -> new StarException("MethodName不能为空"));
        Assert.notNull(transactionDetailReq.getTransactionHash(), () -> new StarException("TransactionHash不能为空"));
        return JSONUtil.toBean(request(transactionDetailReq, ChainConstants.transaction_detail), TransactionDetailRes.class);
    }

    /**
     * 查询用户信息
     * @param chainUserInfoReq
     * @return
     */
  public ChainUserInfoRes userInfo(ChainUserInfoReq chainUserInfoReq) {
        Assert.notNull(chainUserInfoReq.getUserId(), () -> new StarException("userId不能为空"));
        Assert.notNull(chainUserInfoReq.getUserKey(), () -> new StarException("UserKey不能为空"));
        return JSONUtil.toBean(request(chainUserInfoReq, ChainConstants.user_info), ChainUserInfoRes.class);
    }

    public ChainUserInfoRes userUpdateKey(UserUpdateKeyReq chainUserInfoReq) {
        Assert.notNull(chainUserInfoReq.getUserId(), () -> new StarException("userId不能为空"));
        Assert.notNull(chainUserInfoReq.getUserKey(), () -> new StarException("UserKey不能为空"));
        Assert.notNull(chainUserInfoReq.getNewUserKey(), () -> new StarException("NewUserKey不能为空"));
        return JSONUtil.toBean(request(chainUserInfoReq, ChainConstants.user_update_key), ChainUserInfoRes.class);
    }

    private String request(Object req, String uri) {
        JSONObject json = JSONUtil.parseObj(req);
        if (!json.containsKey("appId")) {
            json.set("appId", chainConfiguration.getAppId());
        }
        if (!json.containsKey("appKey")) {
            json.set("appKey", chainConfiguration.getAppKey());
        }
        return HttpUtil.post(chainConfiguration.getGateway().concat(uri), JSONUtil.toJsonStr(json), 5000);
    }

//    }
}
