package com.star.nft.test;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
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
    public  void usercreate(){
        CreateAccountReq createAccountReq = new CreateAccountReq();
        createAccountReq.setUserId("951029971223");
        String userKey = SecureUtil.sha1("951029971223".concat("lywc"));
        createAccountReq.setUserKey(userKey);
        System.out.println(userKey);
        CreateAccountRes account = tiChainServer.createAccount(createAccountReq);
        System.out.println(account);
    }

    @Test
    public  void goodspush(){
        Map<String,Object> map=new HashMap<>();

        List<String> ids=new ArrayList<>();
        int nums=3500;
        long prifix=202210070000L;
        for (int i = 3001; i <= nums; i++) {
            ids.add(String.format("%s",prifix+i));
        }
        map.put("images","https://banner-1302318928.cos.ap-shanghai.myqcloud.com/theme/1664804648034_d999a25b.jpg");
        PublishGoodsReq publishGoodsReq = new PublishGoodsReq();
        publishGoodsReq.setUserId("951029971223");
        String userKey = SecureUtil.sha1("951029971223".concat("lywc"));
        publishGoodsReq.setUserKey(userKey);
        publishGoodsReq.setAuthor("链元文创");
        publishGoodsReq.setProductIds(ids.toArray(new String[ids.size()]));
        publishGoodsReq.setPieceCount(500);
        publishGoodsReq.setInitPrice("0.0");
        publishGoodsReq.setContractAddress("0x467d2d2a55eb0a35bc4da40b109c33b0bd0733a1");
        publishGoodsReq.setName("链元文创 星际探险队 - 普通队员");
        publishGoodsReq.setFeature(JSONUtil.toJsonStr(map));
        PublishGoodsRes createAccountRes = tiChainServer.publishGoods(publishGoodsReq);
        System.out.println(createAccountRes);
    }

    @Test
    public void snowflowId(){
        Long aLong = SnowflakeWorker.generateId();
    }


    @Test
    public  void goodtransfer(){
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
    public  void transferDetail(){
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
    @Test
    public  void userInfo(){

        ChainUserInfoReq goodsTransferReq = new ChainUserInfoReq();
        goodsTransferReq.setUserId("3");
        String userKey = SecureUtil.sha1("3".concat("dasdasd"));
        goodsTransferReq.setUserKey(userKey);

        ChainUserInfoRes chainUserInfoRes = tiChainServer.userInfo(goodsTransferReq);
        System.out.println(chainUserInfoRes);
    }
    @Test
    public  void userUpdateKey(){
        //address
        //0xea5c7de81ee738cc42e8d1040e9d96a9cd92c3b5
        //
        //0x65e75e3f32179675a8d8ce0ca89fce1f56959246


        //to 0xf69Tra13058f4d10843c22f7bb476846e54fe2c5
        UserUpdateKeyReq goodsTransferReq = new UserUpdateKeyReq();
        goodsTransferReq.setUserId("2");

        goodsTransferReq.setUserKey( SecureUtil.sha1("2b".concat("dasdasd")));
        goodsTransferReq.setNewUserKey( SecureUtil.sha1("2b".concat("dasdasd")));

        ChainUserInfoRes chainUserInfoRes = tiChainServer.userUpdateKey(goodsTransferReq);
        System.out.println(chainUserInfoRes);
    }

}
