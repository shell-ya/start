package com.star.nft.test;

import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.SecureUtil;
import com.starnft.star.common.chain.TiChainFactory;

import com.starnft.star.common.chain.config.ChainConfiguration;
import com.starnft.star.common.chain.enums.MethodEnums;
import com.starnft.star.common.chain.model.req.*;
import com.starnft.star.common.chain.model.res.*;
import com.starnft.star.interfaces.StarApplication;

import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.web3j.crypto.Hash;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = {StarApplication.class})
public class TiChainServerTest {
    @Resource
    TiChainFactory tiChainServer;
    @Resource
    ChainConfiguration chainConfiguration;
    @Test
    public  void usercreate(){
        CreateAccountReq createAccountReq = new CreateAccountReq();
        createAccountReq.setUserId("3");
        String userKey = SecureUtil.sha1("3".concat("dasdasd"));
        createAccountReq.setUserKey(userKey);
        System.out.println(userKey);
        CreateAccountRes account = tiChainServer.createAccount(createAccountReq);
        System.out.println(account);
    }

    @Test
    public  void goodspush(){
        PublishGoodsReq publishGoodsReq = new PublishGoodsReq();
        publishGoodsReq.setUserId("2");
        String userKey = SecureUtil.sha1("2".concat("dasdasd"));
        publishGoodsReq.setUserKey(userKey);
        publishGoodsReq.setAuthor("链元文创");
        publishGoodsReq.setPieceCount(10);
        publishGoodsReq.setInitPrice("19.9");
        publishGoodsReq.setName("链元文创-创世爪爪座");
        PublishGoodsRes createAccountRes = tiChainServer.publishGoods(publishGoodsReq);
        System.out.println(createAccountRes);
    }


    @Test
    public  void goodtransfer(){
  //address
        //0xea5c7de81ee738cc42e8d1040e9d96a9cd92c3b5
  //
        //0x65e75e3f32179675a8d8ce0ca89fce1f56959246


        //to 0xf69fb213058f4d10843c22f7bb476846e54fe2c5
        GoodsTransferReq goodsTransferReq = new GoodsTransferReq();
        goodsTransferReq.setUserId("2");
        String userKey = SecureUtil.sha1("2".concat("dasdasd"));
        goodsTransferReq.setUserKey(userKey);
        goodsTransferReq.setFrom("0x65e75e3f32179675a8d8ce0ca89fce1f56959246");
        goodsTransferReq.setTo("0xf69fb213058f4d10843c22f7bb476846e54fe2c5");
        goodsTransferReq.setContractAddress("0xea5c7de81ee738cc42e8d1040e9d96a9cd92c3b5");
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
        //
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
        //address
        //0xea5c7de81ee738cc42e8d1040e9d96a9cd92c3b5
        //
        //0x65e75e3f32179675a8d8ce0ca89fce1f56959246


        //to 0xf69Tra13058f4d10843c22f7bb476846e54fe2c5
        ChainUserInfoReq goodsTransferReq = new ChainUserInfoReq();
        goodsTransferReq.setUserId("2");
        String userKey = SecureUtil.sha1("2".concat("dasdasd"));
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

        goodsTransferReq.setUserKey( SecureUtil.sha1("2".concat("dasdasd")));
        goodsTransferReq.setNewUserKey( SecureUtil.sha1("2b".concat("dasdasd")));

        ChainUserInfoRes chainUserInfoRes = tiChainServer.userUpdateKey(goodsTransferReq);
        System.out.println(chainUserInfoRes);
    }

}
