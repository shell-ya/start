//package com.star.nft.test;
//
//import com.baidu.xasset.auth.XchainAccount;
//import com.baidu.xasset.client.base.BaseDef;
//import com.baidu.xasset.client.xasset.Asset;
//import com.baidu.xasset.client.xasset.XassetDef;
//import com.baidu.xasset.common.config.Config;
//import com.baidu.xuper.api.Account;
//
//import java.util.logging.Logger;
//
//public class TestBaidu {
//    public static void main(String[] args) {
//
//            // 配置AK/SK
//            long appId = 110451;
//            String ak = "9470afbca63fa74505e0aa17c1bd05bd";
//            String sk = "52b9e6902c5fbc4d99d0322770d19649";
//
//            Config.XassetCliConfig cfg = new Config.XassetCliConfig();
//            cfg.setCredentials(appId, ak, sk);
//            cfg.setEndPoint("http://120.48.16.137:8360");
//
//            // 创建区块链账户
//            Account acc = XchainAccount.newXchainEcdsaAccount(XchainAccount.mnemStrgthStrong, XchainAccount.mnemLangEN);
//            // 初始化接口类
//            Asset handle = new Asset(cfg, Logger.getGlobal());
//            // 调用方法
//            BaseDef.Resp<XassetDef.GetStokenResp> result = handle.getStoken(acc);
//            System.out.println(result);
//        }
//
//    }
//
