package com.starnft.star.interfaces.controller.rank;

import com.starnft.star.application.process.rank.IRankProcessor;
import com.starnft.star.application.process.rank.req.RankReq;
import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.RequestConditionPage;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistoryItem;
import com.starnft.star.domain.rank.core.rank.model.res.Rankings;
import com.starnft.star.domain.rank.core.rank.model.res.RankingsItem;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Date 2022/7/6 10:47 PM
 * @Author ： shellya
 */
@RestController
@Api(tags = "排行榜接口「RankController」")
@RequestMapping(value = "/rank")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RankController {

    final IRankProcessor rankProcessor;
    @ApiOperation("获取排行榜数据")
    @PostMapping("/rankings")
    @TokenIgnore
    public RopResponse<ResponsePageResult<RankingsItem>> rankings(@RequestBody RequestConditionPage<RankReq> request) {

//
//        Rankings rankings = new Rankings();
//        rankings.setRankName("邀请");
//
//        List<RankingsItem> rankingsItems = new ArrayList<>();
//        for (int i = 0; i < 300; i++) {
//            RankingsItem rankingsItem = new RankingsItem();
//            rankingsItem.setAccount( RandomUtil.randomLong(9));
//            rankingsItem.setPhone(RandomUtil.randomPhone());
//            rankingsItem.setTotal(RandomUtil.randomInt(10,1000));
//            rankingsItem.setValid(RandomUtil.randomInt(5,rankingsItem.getTotal()));
//            rankingsItems.add(rankingsItem);
//        }
//        rankingsItems = rankingsItems.stream().sorted(Comparator.comparing(RankingsItem::getValid,Comparator.reverseOrder())
////                .thenComparing(RankingsItem::getValid,Comparator.reverseOrder())
//        ).collect(Collectors.toList());
//        rankings.setRankItem(rankingsItems);
        return RopResponse.success(rankProcessor.rankings(request));
    }


    @ApiOperation("邀请记录")
    @PostMapping("/history")
    public RopResponse< List<InvitationHistoryItem>> history(@RequestBody RequestConditionPage<RankReq> request){
//        InvitationHistory invitationHistory = new InvitationHistory();
//        List<InvitationHistoryItem> items = new ArrayList<>();
//
//        for (int i = 0; i < 40; i++) {
//            InvitationHistoryItem item = new InvitationHistoryItem();
//            item.setPhone(RandomUtil.randomPhone());
//            item.setValid((int) (Long.parseLong(item.getPhone()) % 2));
//            items.add(item);
//        }
//        invitationHistory.setItems(items);
        request.getCondition().setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(rankProcessor.invitationHistory(request));
    }

    @ApiOperation("个人排名")
    @PostMapping("/person")
    public RopResponse<Long> getPersonNum(@RequestBody RankReq rankReq){
        rankReq.setUserId(UserContext.getUserId().getUserId());
        return RopResponse.success(rankProcessor.personNum(rankReq));
    }

}
