package com.starnft.star.application.mq.producer.marketorder;

import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.application.process.number.req.MarketOrderStatus;
import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Date 2022/6/17 6:01 PM
 * @Author ： shellya
 */
@Service
public class MarketOrderProducer extends BaseProducer {

    /**
     * 关闭订单
     *
     * @param status 订单状态
     */
    public void marketOrderRollback(MarketOrderStatus status) {

        String destination = String.format(TopicConstants.MARKET_ORDER_ROLLBACK_DESTINATION.getFormat(), TopicConstants.MARKET_ORDER_ROLLBACK_DESTINATION.getTag());

        messageSender.syncSendDelay(destination, Optional.of(status), 3000L, 7);
    }

}
