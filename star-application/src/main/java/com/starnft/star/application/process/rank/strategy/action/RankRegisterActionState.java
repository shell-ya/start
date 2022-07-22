package com.starnft.star.application.process.rank.strategy.action;

import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.event.model.res.EventActivityExtRes;
import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import com.starnft.star.domain.rank.core.rank.model.RankItemMetaData;
import com.starnft.star.domain.user.model.vo.UserInfo;
import com.starnft.star.domain.user.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class RankRegisterActionState implements IRankActionState {
    @Resource
    IRankService iRankService;
    @Resource
    IUserRepository iUserRepository;
    public StarConstants.EventSign getState(){
        return StarConstants.EventSign.Register;
    }
    public void manage(ActivityEventReq activityEventReq,RankDefinition rankDefinition, EventActivityExtRes extArrays){
        Map<String, Object> params = activityEventReq.getParams();
        if (Objects.isNull(params)){
            return;
        }
        Object parentObject = params.get("parent");
        if (Objects.isNull(parentObject)){
            return;
        }
        String rankName = rankDefinition.getRankName();
        Long parent = Long.parseLong(parentObject.toString());
        Long number = Long.parseLong(Optional.ofNullable(params.get("number")).orElse(1).toString()) ;
        //处理附加数据
        RankItemMetaData rankItemMetaData= extractedRankMetaData(rankDefinition, activityEventReq);
        if (Objects.isNull(rankItemMetaData)){
            log.info("查询不到信息");
            return;
        }
        UserInfo parentUser = iUserRepository.queryUserInfoByUserId(parent);
        if (Objects.isNull(parentUser)){
            log.info("未找到邀请人:{},用户id:{}",parent,rankItemMetaData.getChildrenId());
            return;
        }
        iRankService.setUserPhoneMapping(rankName,parent.toString(),parentUser.getPhone());
        iRankService.setUserPhoneMapping(rankName,rankItemMetaData.getChildrenId().toString(),rankItemMetaData.getMobile());
        iRankService.put(rankName, parent.toString(),number.doubleValue(),rankItemMetaData);
    }

    private RankItemMetaData extractedRankMetaData(RankDefinition rankDefinition, ActivityEventReq activityEventReq) {

        if (rankDefinition.getIsExtend().equals(StarConstants.EventStatus.EVENT_STATUS_OPEN)){
            RankItemMetaData rankItemMetaData= new RankItemMetaData();
            log.info("排行版拥有附加数据");
            UserInfo userInfo = iUserRepository.queryUserInfoByUserId(activityEventReq.getUserId());
            if (Objects.isNull(userInfo)){
                return null;
            }
            rankItemMetaData.setChildrenId(userInfo.getAccount());
            rankItemMetaData.setNickName(userInfo.getNickName());
            rankItemMetaData.setMobile(userInfo.getPhone());
            rankItemMetaData.setAvatar(userInfo.getAvatar());
            rankItemMetaData.setInvitationTime(activityEventReq.getReqTime());
            return rankItemMetaData;
        }
        return null;
    }
}
