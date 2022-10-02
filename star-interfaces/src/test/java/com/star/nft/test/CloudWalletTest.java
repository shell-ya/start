package com.star.nft.test;


import com.starnft.star.application.process.wallet.impl.CloudWalletCore;
import com.starnft.star.application.process.wallet.req.OpenCloudAccountReq;
import com.starnft.star.domain.payment.model.res.CloudAccountStatusRes;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {StarApplication.class})
public class CloudWalletTest {

    @Autowired
    private CloudWalletCore walletCore;

    @Test
    public void Test(){
        OpenCloudAccountReq openCloudAccountReq = new OpenCloudAccountReq();
        openCloudAccountReq.setReturnUri("https%3A%2F%2Fcmdev.infiart.cn%2F%23%2Fpages%2Fwallet%2FselectWallet");
        walletCore.openCloudWallet(281850262L,openCloudAccountReq);
    }

    @Test
    public void openCloudStatus(){
        CloudAccountStatusRes cloudAccountStatusRes = walletCore.cloudWalletStatus(536952750L);
    }
}
