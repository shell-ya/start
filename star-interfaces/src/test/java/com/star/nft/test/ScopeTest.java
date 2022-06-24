package com.star.nft.test;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.UserScopeMessageVO;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(classes = {StarApplication.class})
public class ScopeTest {
    @Autowired
     IMessageSender messageSender;
    @Autowired
    IScopeCore iScopeCore;
@Test
    public  void  addMessageScope(){
    String rechargeCallbackProcessTopic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),
        TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());
        UserScopeMessageVO userScopeMessageVO = new UserScopeMessageVO();
        userScopeMessageVO.setScope(new BigDecimal(11));
        userScopeMessageVO.setUserId(9830832260L);
        userScopeMessageVO.setEventGroup(1);
       messageSender.send(rechargeCallbackProcessTopic, Optional.of(userScopeMessageVO));
    }

    @Test
    public  void  addScope(){

        UserScopeMessageVO userScopeMessageVO = new UserScopeMessageVO();
        userScopeMessageVO.setScope(new BigDecimal(11));
        userScopeMessageVO.setUserId(9830832260L);
        userScopeMessageVO.setEventGroup(1);
        iScopeCore.calculateUserScope(userScopeMessageVO);
    }
}
