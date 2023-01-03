package com.star.nft.test;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.esotericsoftware.minlog.Log;
import com.starnft.star.common.chain.TiChainFactory;

import com.starnft.star.common.chain.config.ChainConfiguration;
import com.starnft.star.common.chain.enums.MethodEnums;
import com.starnft.star.common.chain.model.req.*;
import com.starnft.star.common.chain.model.res.*;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.interfaces.StarApplication;

import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.web3j.crypto.Hash;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {StarApplication.class})
public class TiChainServerTest {

    @Resource
    TiChainFactory tiChainServer;
    @Resource
    ChainConfiguration chainConfiguration;

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
    public void goodspush() {
        Map<String, Object> map = new HashMap<>();

        List<String> ids = new ArrayList<>();
        int nums = 1;
        long prifix = 20221008000L;
        for (int i = 1; i <= nums; i++) {
            ids.add(String.format("%s", prifix + i));
        }
        map.put("images", "https://banner-1302318928.cos.ap-shanghai.myqcloud.com/theme/1664955735505_4a2d4872.jpg");
        PublishGoodsReq publishGoodsReq = new PublishGoodsReq();
        publishGoodsReq.setUserId("951029971223");
        String userKey = SecureUtil.sha1("951029971223".concat("lywc"));
        publishGoodsReq.setUserKey(userKey);
        publishGoodsReq.setAuthor("链元文创");
        publishGoodsReq.setProductIds(ids.toArray(new String[ids.size()]));
        publishGoodsReq.setPieceCount(nums);
        publishGoodsReq.setInitPrice("0.0");
        // publishGoodsReq.setContractAddress("0x467d2d2a55eb0a35bc4da40b109c33b0bd0733a1");
        publishGoodsReq.setName("链元文创 星际探索者 - 月球指挥官123");
        publishGoodsReq.setFeature(JSONUtil.toJsonStr(map));
        PublishGoodsRes createAccountRes = tiChainServer.publishGoods(publishGoodsReq);
        System.out.println(createAccountRes);
    }

    @Test
    public void goodsPublish() {
        Map<String, Object> map = new HashMap<>();
        map.put("images", "https://banner-1302318928.cos.ap-shanghai.myqcloud.com/theme/1658216372585_a44170ed.jpg");
        PublishGoodsReq publishGoodsReq = new PublishGoodsReq();
        publishGoodsReq.setUserId("951029971223");
        String userKey = SecureUtil.sha1("951029971223".concat("lywc"));
        publishGoodsReq.setUserKey(userKey);
        publishGoodsReq.setAuthor("链元文创");
        publishGoodsReq.setPieceCount(1);
        publishGoodsReq.setInitPrice("1.99");
        publishGoodsReq.setName("新人徽章(每人限购一枚)");
        publishGoodsReq.setFeature(JSONUtil.toJsonStr(map));
        PublishGoodsRes publishGoods = tiChainServer.publishGoods(publishGoodsReq);
        System.out.println(publishGoods);
    }

    /**
     * 2022-12-30 22:12:02.597 [main][] INFO  c.s.star.common.chain.TiChainFactory.request - url:https://api.tichain.tianhecloud.com/api/v2/nfr/publish
     * 2022-12-30 22:12:02.597 [main][] INFO  c.s.star.common.chain.TiChainFactory.request - param:{"author":"链元文创","initPrice":"0.0","userId":"951029971223","userKey":"1589abfa7ea9939e0b15443b2abc650e8432d5a0","pieceCount":1,"feature":"{\"images\":\"https://banner-1302318928.cos.ap-shanghai.myqcloud.com/theme/1664955735505_4a2d4872.jpg\"}","productIds":["20221008001"],"appId":"tichain449113","name":"链元文创 星际探索者 - 月球指挥官123","appKey":"a070713d25c291127992bf096cbcb3462ca1e33c"}
     * 2022-12-30 22:12:02.597 [main][] INFO  c.s.star.common.chain.TiChainFactory.request - result:{"code":0,"message":"success","data":{"contractAddress":"0x8a61ccd6d4763a9b2e3ed7b7548cf5836b3ea093","deployTransactionHash":"0xa146aa92f9fe1bff959d3d79edeb33827a8f1ac67d01b2aa11f331c5eac06958","products":[{"transactionHash":"0xb5319a5e0adfbfc0b2475329756759c4171cd45f51b07cebefacd2adb5a2456c","productId":"20221008001","address":"0x58d7d10ac44ceba9a51dfc6baf9f783d61817a96","tokenId":0,"initPrice":"0.0"}]}}
     * PublishGoodsRes(code=0, message=success, data=PublishGoodsRes.DataDTO(contractAddress=0x8a61ccd6d4763a9b2e3ed7b7548cf5836b3ea093, deployTransactionHash=0xa146aa92f9fe1bff959d3d79edeb33827a8f1ac67d01b2aa11f331c5eac06958, products=[PublishGoodsRes.DataDTO.ProductsDTO(transactionHash=0xb5319a5e0adfbfc0b2475329756759c4171cd45f51b07cebefacd2adb5a2456c, productId=20221008001, address=0x58d7d10ac44ceba9a51dfc6baf9f783d61817a96, tokenId=0, initPrice=0.0)]))
     */

    @Test
    public void snowflowId() {
        Long aLong = SnowflakeWorker.generateId();
    }


    @Test
    public void goodtransfer() {
        //address
        //0xf69fb213058f4d10843c22f7bb476846e54fe2c5
        //
        //0x65e75e3f32179675a8d8ce0ca89fce1f56959246


        //to 0xf69fb213058f4d10843c22f7bb476846e54fe2c5
        GoodsTransferReq goodsTransferReq = new GoodsTransferReq();
        goodsTransferReq.setUserId("3");
        String userKey = SecureUtil.sha1("3".concat("dasdasd"));
        goodsTransferReq.setUserKey(userKey);
        goodsTransferReq.setFrom("0xf69fb213058f4d10843c22f7bb476846e54fe2c5");
        goodsTransferReq.setTo("0x231042f4c3636bbf7b91622ba6808ef538309d01");
        goodsTransferReq.setContractAddress("0xfdb23570c13e3d706470ea9e224a95e43c0af0f7");
        goodsTransferReq.setTokenId("2");
//        goodsTransferReq.setPieceCount(10);
//        goodsTransferReq.setInitPrice("19.9");
//        goodsTransferReq.setName("链元文创-创世白羊座");
        GoodsTransferRes createAccountRes = tiChainServer.goodsTransfer(goodsTransferReq);
        System.out.println(createAccountRes);
    }


    @Test
    public void transferDetail() {
        //address
        //0xea5c7de81ee738cc42e8d1040e9d96a9cd92c3b5
        //3
        //0x65e75e3f32179675a8d8ce0ca89fce1f56959246


        //to 0xf69Tra13058f4d10843c22f7bb476846e54fe2c5
        TransactionDetailReq goodsTransferReq = new TransactionDetailReq();
        goodsTransferReq.setUserId("2");
        String userKey = SecureUtil.sha1("2".concat("dasdasd"));
        goodsTransferReq.setUserKey(userKey);
        goodsTransferReq.setMethodName(MethodEnums.mint);
        goodsTransferReq.setTransactionHash("0x61872c37c9e53264b1c8304eacb5df33f2560bc3272a02fd159e7bb60fb7e90a");
//        goodsTransferReq.setPieceCount(10);
//        goodsTransferReq.setInitPrice("19.9");
//        goodsTransferReq.setName("链元文创-创世白羊座");
        TransactionDetailRes transactionDetailRes = tiChainServer.transferDetail(goodsTransferReq);
        System.out.println(transactionDetailRes);
    }

    // url:https://api.tichain.tianhecloud.com/api/v2/user/info
    // param:{"userId":"951029971223","userKey":"1589abfa7ea9939e0b15443b2abc650e8432d5a0","appId":"tichain449113","appKey":"a070713d25c291127992bf096cbcb3462ca1e33c"}
    // result:{"code":0,"message":"success","data":{"publicKey":"04bfe777532dc023feefa66ab2fd1131ee65a8c600dd9b8af22c8393eb3e1275dc00db2fe884ab27bb6e8012d7e4ef436e0b09a863b9fd0df40dddc68ae18ed18e",
    // "address":"0x58d7d10ac44ceba9a51dfc6baf9f783d61817a96"}}


    @Test
    public void goodTransferNew() {
        GoodsTransferReq goodsTransferReq = new GoodsTransferReq();
        goodsTransferReq.setUserId("951029971223");
        String userKey = SecureUtil.sha1("951029971223".concat("lywc"));
        goodsTransferReq.setUserKey(userKey);
        goodsTransferReq.setFrom("0x58d7d10ac44ceba9a51dfc6baf9f783d61817a96");
        goodsTransferReq.setTo("0xbeda63cf97aaaa9b982d64a08dc2bdefcd0215d3");
        goodsTransferReq.setContractAddress("0x68ea67ec38c43acf46d926f939bf5695d0a2e0d8");
        goodsTransferReq.setTokenId("77");
        GoodsTransferRes createAccountRes = tiChainServer.goodsTransfer(goodsTransferReq);
        System.out.println(createAccountRes);
    }

    @Test
    public void tokenOwner() {

        TokenQueryReq req = new TokenQueryReq();
        req.setTokenId(77);
        req.setContractAddress("0x68ea67ec38c43acf46d926f939bf5695d0a2e0d8");
        TokenQueryRes res = tiChainServer.tokenQuery(req);
        Log.info(JSONUtil.toJsonStr(res));

    }

    @Test
    public void userInfo() {

        ChainUserInfoReq goodsTransferReq = new ChainUserInfoReq();
        goodsTransferReq.setUserId("951029971223");
        String userKey = SecureUtil.sha1("951029971223".concat("lywc"));
        goodsTransferReq.setUserKey(userKey);
        ChainUserInfoRes chainUserInfoRes = tiChainServer.userInfo(goodsTransferReq);
        System.out.println(chainUserInfoRes);
    }

    @Test
    public void userUpdateKey() {
        //address
        //0xea5c7de81ee738cc42e8d1040e9d96a9cd92c3b5
        //
        //0x65e75e3f32179675a8d8ce0ca89fce1f56959246


        //to 0xf69Tra13058f4d10843c22f7bb476846e54fe2c5
        UserUpdateKeyReq goodsTransferReq = new UserUpdateKeyReq();
        goodsTransferReq.setUserId("2");

        goodsTransferReq.setUserKey(SecureUtil.sha1("2b".concat("dasdasd")));
        goodsTransferReq.setNewUserKey(SecureUtil.sha1("2b".concat("dasdasd")));

        ChainUserInfoRes chainUserInfoRes = tiChainServer.userUpdateKey(goodsTransferReq);
        System.out.println(chainUserInfoRes);
    }

}
