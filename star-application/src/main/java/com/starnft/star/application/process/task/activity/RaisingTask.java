package com.starnft.star.application.process.task.activity;

import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.domain.number.model.vo.RaisingTheme;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.raising.service.IRaisingService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class RaisingTask {

    @Resource
    private IOrderService orderService;

    @Resource
    private INumberService numberService;

    @Resource
    private IRaisingService raisingService;

    @Resource
    private TransactionTemplate template;
//    1.开盘价设置为昨日成交中位数
//    2.无成交记录以地板价作为开盘价
//    todo 3.昨日无成交记录无挂单无限制挂单
//    4.涨停后下架该主题藏品，明日才可挂售
//    5.寄售时返回涨停价
//    todo 6.新放开寄售的藏品以官方价设为开盘价

    @XxlJob("raisingTask")
    public void raising() {
        //查询所有主题
        List<RaisingTheme> raisingThemes = numberService.nowRaisingTheme();
        for (RaisingTheme theme :
                raisingThemes) {
            //以当前时间查询昨日成交价
            List<BigDecimal> prices = orderService.dealOrderPrice(theme.getThemeInfoId(), DateUtil.addDateHour(new Date(), -24));
            //昨日成交不为空 取中位值
            if (!prices.isEmpty()) {
                theme.setFloorPrice(prices.get(prices.size() / 2));
            }else{
                //挂售中最低价
                BigDecimal minPrice = numberService.getconsignMinPrice(theme.getThemeInfoId());
                if (null != minPrice)  theme.setFloorPrice(minPrice);
            }
            //存藏品开盘价 涨停价 开盘日期
            raisingService.saveRaising(theme);
        }
    }

    //挂售中藏品已是当日涨停价
    //设置为已涨停 下架该主题市场挂售藏品
    @XxlJob("checkRaisin")
    public void checkRaisin() {
        //目前挂售最低价
        List<ThemeNumberVo> minNumbers = numberService.getMinConsignNumberDetail();
        //查询主题当日涨停价
        for (ThemeNumberVo numberVo :
                minNumbers) {
            //地板价已到涨停价
            RaisingTheme raisingTheme = raisingService.nowRaisingTheme(numberVo.getThemeInfoId());
            if (Objects.nonNull(raisingTheme) &&
                    numberVo.getPrice().compareTo(raisingTheme.getLimitPrice()) >= 0 &&
                    raisingTheme.getIsRaising().equals(Boolean.FALSE)) {
                template.execute(status -> {
                    //更新主题当日涨停涨停标志
                    boolean flag = raisingService.updateRaisingFlag(numberVo.getThemeInfoId());
                    //下架挂售藏品
                    boolean takeDown = numberService.takeDownTheme(numberVo.getThemeInfoId());
                    return flag && takeDown;
                });
            }
        }
    }
}
