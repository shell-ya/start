package com.starnft.star.application.mq.producer.order;

import com.starnft.star.application.mq.constant.TopicConstants;
import com.starnft.star.application.mq.producer.BaseProducer;
import com.starnft.star.application.process.number.req.MarketOrderStatus;
import com.starnft.star.application.process.order.model.dto.OrderMessageReq;
import com.starnft.star.application.process.order.model.res.OrderGrabStatus;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class OrderProducer extends BaseProducer {

    /**
     * MQ异步下单
     *
     * @param message 下单请求
     */
    public void secKillOrder(OrderMessageReq message) {

        String destination = String.format(TopicConstants.ORDER_SEC_KILL_DESTINATION.getFormat(), TopicConstants.ORDER_SEC_KILL_DESTINATION.getTag());

        messageSender.asyncSend(destination, Optional.of(message), success(), rollback());
    }

    /**
     * MQ3分钟关闭订单
     *
     * @param status 订单状态
     */
    public void secOrderRollback(OrderGrabStatus status) {

        String destination = String.format(TopicConstants.ORDER_SEC_KILL_ROLLBACK_DESTINATION.getFormat(), TopicConstants.ORDER_SEC_KILL_ROLLBACK_DESTINATION.getTag());

        messageSender.syncSendDelay(destination, Optional.of(status), 3000L, 9);
    }

    /**
     * 关闭订单
     *
     * @param status 订单状态
     */
    public void marketOrderRollback(MarketOrderStatus status) {

        String destination = String.format(TopicConstants.MARKET_ORDER_ROLLBACK_DESTINATION.getFormat(), TopicConstants.MARKET_ORDER_ROLLBACK_DESTINATION.getTag());

        messageSender.syncSendDelay(destination, Optional.of(status), 3000L, 9);
    }

    private Consumer<SendResult> success() {
        return sendResult -> {

        };
    }

    private Runnable rollback() {
        return () -> {

        };
    }


}
