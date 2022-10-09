package com.starnft.star.application.process.task.activity;

import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.domain.number.model.vo.RaisingTheme;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RaisingTask {

    @Resource
    private IOrderService orderService;

    @Resource
    private INumberService numberService;

//    1.开盘价设置为昨日成交中位数
//    2.无成交记录以地板价作为开盘价
//    todo 3.昨日无成交记录无挂单无限制挂单
//    4.涨停后下架该主题藏品，明日才可挂售
//    5.寄售时返回涨停价
//    todo 6.新放开寄售的藏品以官方价设为开盘价


    public void raising(){
        //查询每个可挂售藏品的地板价
        List<RaisingTheme> raisingThemes = numberService.nowRaisingTheme();
        for (RaisingTheme theme :
                raisingThemes) {
            //以当前时间查询昨日成交价
            List<BigDecimal> prices = orderService.dealOrderPrice(theme.getThemeInfoId(), DateUtil.addDateHour(new Date(),-24));
            if (prices.isEmpty())continue;
            //取出中位值
            theme.setFloorPrice(prices.get(prices.size() / 2));
        }
        //存藏品开盘价 涨停价 开盘日期

    }


}
