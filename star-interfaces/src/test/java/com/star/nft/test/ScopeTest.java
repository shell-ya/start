package com.star.nft.test;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.AddScoreDTO;
import com.starnft.star.common.utils.VirtualModelUtils;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = {StarApplication.class})
public class ScopeTest {
    @Autowired
     IMessageSender messageSender;
    @Autowired
    IScopeCore iScopeCore;
//@Test
//    public  void  addMessageScope(){
//    String rechargeCallbackProcessTopic = String.format(TopicConstants.CREDITS_PROCESS_DESTINATION.getFormat(),
//        TopicConstants.CREDITS_PROCESS_DESTINATION.getTag());
//        UserScopeMessageVO userScopeMessageVO = new UserScopeMessageVO();
//        userScopeMessageVO.setScope(new BigDecimal(11));
//        userScopeMessageVO.setUserId(9830832260L);
//        userScopeMessageVO.setEvent(1);
//       messageSender.send(rechargeCallbackProcessTopic, Optional.of(userScopeMessageVO));
//    }
//
    @Test
    public  void  addScope(){
        for (int i = 0; i < 160; i++) {
//            AddScoreDTO addScoreDTO = new AddScoreDTO();
//            addScoreDTO.setScope(new BigDecimal(11));
//            addScoreDTO.setUserId(305730346L);
//            addScoreDTO.setScopeType(0);
//            addScoreDTO.setTemplate("参加拉新活动获得%s积分");
//            iScopeCore.userScopeManageAdd(addScoreDTO);
            AddScoreDTO addScoreDTO = new AddScoreDTO();
            addScoreDTO.setScope(new BigDecimal("1.0"));
            addScoreDTO.setUserId(305730346L);
            addScoreDTO.setScopeType(2);
            addScoreDTO.setTemplate("日常活跃获得%s积分");
            iScopeCore.userScopeManageAdd(addScoreDTO);

            AddScoreDTO scoreDTO = new AddScoreDTO();
            scoreDTO.setScope(new BigDecimal("1.0"));
            scoreDTO.setUserId(305730346L);
            scoreDTO.setScopeType(3);
            scoreDTO.setTemplate("商品流转获得%s积分");
            iScopeCore.userScopeManageAdd(scoreDTO);
        }
    }



    @Test
    public void testVis(){

        Map<String,String[]> maps=new HashMap<>();
        String[] values=new String[1];
        values[0]="1";
        maps.put("names",values);
        VirtualModelUtils virtualModelUtils = new VirtualModelUtils();
        Object o = virtualModelUtils.virtualEntityEncapsulation(maps);
        System.out.println(o);
    }
}
