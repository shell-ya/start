package com.star.nft.test;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.process.event.model.EventReqAssembly;
import com.starnft.star.application.process.event.model.ScopeEventReq;
import com.starnft.star.interfaces.StarApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(classes = {StarApplication.class})
@Slf4j
public class TestSms {
    @Resource
    IMessageSender messageSender;
    @Test
    public void pullScopeTest(){
        ScopeEventReq scopeEventReq = new ScopeEventReq();
        scopeEventReq.setEventSign("pull_new_activity");
        scopeEventReq.setEventSign("scope");
        scopeEventReq.setUserId(1L);
        scopeEventReq.setNumber(new BigDecimal(12));
        String scopeTopic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),
                TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());

        messageSender.send(scopeTopic, Optional.of( EventReqAssembly.assembly(scopeEventReq)));
    }

}
