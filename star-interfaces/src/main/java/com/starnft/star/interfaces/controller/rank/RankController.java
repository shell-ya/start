package com.starnft.star.interfaces.controller.rank;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistory;
import com.starnft.star.domain.rank.core.rank.model.res.InvitationHistoryItem;
import com.starnft.star.domain.rank.core.rank.model.res.Rankings;
import com.starnft.star.domain.rank.core.rank.model.res.RankingsItem;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import com.starnft.star.interfaces.interceptor.UserResolverInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Date 2022/7/6 10:47 PM
 * @Author ： shellya
 */
@RestController
@Api(tags = "排行榜接口「RankController」")
@RequestMapping(value = "/rank")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RankController {

    @ApiOperation("获取排行棒数据")
    @GetMapping("/rankings")
    @TokenIgnore
    public RopResponse<Rankings> getBalance(@RequestParam(name = "rankName") String rankName) {
        Rankings rankings = new Rankings();
        rankings.setRankName("邀请");

        List<RankingsItem> rankingsItems = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            RankingsItem rankingsItem = new RankingsItem();
            rankingsItem.setAccount( RandomUtil.randomLong(9));
            rankingsItem.setPhone(RandomUtil.randomPhone());
            rankingsItem.setTotal(RandomUtil.randomInt(10,1000));
            rankingsItem.setValid(RandomUtil.randomInt(5,rankingsItem.getTotal()));
            rankingsItems.add(rankingsItem);
        }
        rankingsItems = rankingsItems.stream().sorted(Comparator.comparing(RankingsItem::getValid,Comparator.reverseOrder())
//                .thenComparing(RankingsItem::getValid,Comparator.reverseOrder())
        ).collect(Collectors.toList());
        rankings.setRankItem(rankingsItems);
        return RopResponse.success(rankings);
    }


    @ApiOperation("邀请记录")
    @GetMapping("/history")
    @TokenIgnore
    public RopResponse<InvitationHistory> history(){
        InvitationHistory invitationHistory = new InvitationHistory();
        List<InvitationHistoryItem> items = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            InvitationHistoryItem item = new InvitationHistoryItem();
            item.setPhone(RandomUtil.randomPhone());
            item.setValid((int) (Long.parseLong(item.getPhone()) % 2));
            items.add(item);
        }
        invitationHistory.setItems(items);
        return RopResponse.success(invitationHistory);
    }

}
