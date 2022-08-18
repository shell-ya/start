package com.starnft.star.application.process.rank.strategy.action;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.event.model.ActivityEventReq;
import com.starnft.star.application.process.event.model.BuyActivityEventReq;
import com.starnft.star.application.process.scope.IScopeCore;
import com.starnft.star.application.process.scope.model.ScoreDTO;
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
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class RankBuyActionState implements  IRankActionState {
    @Resource
    IRankService iRankService;
    @Resource
    IUserRepository iUserRepository;
    @Resource
    IScopeCore scopeCore;
    @Override
    public StarConstants.EventSign getState() {
        return StarConstants.EventSign.Buy;
    }
    @Override
    public void manage(ActivityEventReq activityEventReq, RankDefinition rankDefinition, EventActivityExtRes extArrays) {

        log.info("进入拉新排行版购买动作");
        Map<String, Object> params = activityEventReq.getParams();
        BuyActivityEventReq buyActivityEventReq = BeanUtil.mapToBean(params, BuyActivityEventReq.class, true);
        log.info("进入参数为「{}」", JSONUtil.toJsonStr(buyActivityEventReq));
        UserInfo userInfo = iUserRepository.queryUserInfoByUserId(buyActivityEventReq.getUserId());
        if (Objects.isNull(userInfo)){
            log.info("用户「{}」不存在",activityEventReq.getUserId());
            return;
        }
        if (Objects.isNull(userInfo.getParent())){
            log.info("用户「{}」上级不存在不存在",activityEventReq.getUserId());
            return;
        }
        String extConfigs = extArrays.getParams();
        if (Objects.isNull(extConfigs)){
            log.info("ext 配置为空");
        }
        JSONObject extConfigsParams = JSONUtil.parseObj(extConfigs);
        Boolean isCheckThemeId = extConfigsParams.getBool("isCheckThemeId", false);
        if (isCheckThemeId){
            Long themeId = extConfigsParams.getLong("themeId");
            if (!buyActivityEventReq.getThemeId().equals(themeId)){
                log.info("检测主题不一致");
                return;
            }
        }
        //主题一致 更新上级用户有效邀请
        String rankName = rankDefinition.getRankName();
        Long parent = userInfo.getParent();
        Long number = Long.parseLong(Optional.ofNullable(params.get("number")).orElse(1).toString()) ;
        //处理附加数据
        RankItemMetaData rankItemMetaData= extractedRankMetaData(rankDefinition, activityEventReq);
        if (Objects.isNull(rankItemMetaData)){
            log.info("查询不到信息");
            return;
        }
        //加排行榜分数
        iRankService.validPut(rankName, parent.toString(),number.doubleValue(),rankItemMetaData);
        iRankService.putBuyNum(rankName,rankItemMetaData.getChildrenId());
        //加购买次数
//        iRankService.putBuyNum(rankName,rankItemMetaData);
        ScoreDTO addScoreDTO = compensationScope(parent, rankItemMetaData.getChildrenId());
        if (Objects.isNull(addScoreDTO)){
            log.info("已处理被邀请人积分");
            return;
        }
        scopeCore.userScopeManageAdd(addScoreDTO);
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

    private ScoreDTO compensationScope(Long parent , Long childrenId){
        //检查redis 当前childrenId 是否命中 命中不处理
        boolean userExist = iRankService.scopeUserExist(parent, childrenId);
        if (userExist) return null;
        //设置scopeMapping
        boolean putSuccess = iRankService.putScopeUser(parent, childrenId);
        if (Boolean.FALSE.equals(putSuccess)) return null;
        //配置积分数据

        ScoreDTO addScoreDTO = new ScoreDTO();

        addScoreDTO.setScope(new BigDecimal(100));
        addScoreDTO.setScopeType(0);
        addScoreDTO.setUserId(parent);
        addScoreDTO.setTemplate("拉新活动-邀请获得%s元石");
        return addScoreDTO;
    }
}
