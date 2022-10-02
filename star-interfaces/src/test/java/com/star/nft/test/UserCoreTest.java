package com.star.nft.test;

import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.application.process.user.req.UserLoginReq;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {StarApplication.class})
public class UserCoreTest {

    @Autowired
    private UserCore userCore;

    @Test
    public void test(){
        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.setLoginScenes(2);
        userLoginReq.setPhone(RandomUtil.randomPhone());
        userLoginReq.setPassword("123456");
        userLoginReq.setSc("BQQMCXKV");
        userCore.loginByPhoneAndRegister(userLoginReq);
    }
}
