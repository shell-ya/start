package com.star.nft.test;

import com.starnft.star.application.mq.IMessageSender;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.AddScoreDTO;
import com.starnft.star.application.process.user.UserCore;
import com.starnft.star.common.utils.VirtualModelUtils;
import com.starnft.star.domain.order.model.res.OrderListRes;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.domain.user.service.IUserService;
import com.starnft.star.interfaces.StarApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@SpringBootTest(classes = {StarApplication.class})
public class ScopeTest {
    @Autowired
     IMessageSender messageSender;
    @Autowired
    IScopeCore iScopeCore;
    @Autowired
    IOrderService orderService;
    @Autowired
    IUserService userService;
    @Resource
    IRankService rankService;
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

    @Test
    public void scope(){
        //查询所有新人徽章的订单
        List<OrderVO> orderListRes = orderService.queryAllSuccessOrder();
        log.info("支付成功的新人徽章订单数量:{}",orderListRes.size());
        for (OrderVO order :
                orderListRes) {
            Long ownerBy = order.getUserId();
            //判断购买人有parent字段
            Long parent = userService.queryHasParent(ownerBy);
            //有添加购买人积分
            if (Objects.isNull(parent)) continue;
            //检查redis 当前childrenId 是否命中 命中不处理
            boolean userExist = rankService.scopeUserExist(parent, ownerBy);
            if (userExist) continue;
            //设置scopeMapping
            boolean putSuccess = rankService.putScopeUser(parent, ownerBy);
            if (Boolean.FALSE.equals(putSuccess)) continue;
            //配置积分数据

            AddScoreDTO addScoreDTO = new AddScoreDTO();

            addScoreDTO.setScope(new BigDecimal(300));
            addScoreDTO.setScopeType(0);
            addScoreDTO.setUserId(parent);
            addScoreDTO.setTemplate("参加拉新-邀请活动获得%s元石");

            iScopeCore.userScopeManageAdd(addScoreDTO);
        }
    }
}
