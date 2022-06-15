package com.starnft.star.domain.payment.model.req;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRich implements Serializable {

    /**
     * 支付渠道
     */
    private String payChannel;
    private String bankNo;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 客户端ip
     */
    private String clientIp;
    /**
     * 支付后跳转页面url
     */
    private String frontUrl;
    /**
     * 支付金额
     */
    private BigDecimal totalMoney;
    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 消息广播主题
     */
    private String multicastTopic;

    /**
     * 订单类型
     */
    @Deprecated
    private StarConstants.OrderType orderType;


    public String getOrderTypeName() {
        return orderType.getName();
    }

    public String getOrderTypeDesc() {
        return orderType.getDesc();
    }

    public String composeCallback() {
        HashMap<@Nullable String, @Nullable String> extInfo = Maps.newHashMap();
        extInfo.put("userId", String.valueOf(getUserId()));
//        extInfo.put("orderType", getOrderTypeName());
        extInfo.put("payChannel", getPayChannel());
        extInfo.put("multicastTopic", multicastTopic);
        char[] chars = new JsonStringEncoder().quoteAsString(JSON.toJSONString(extInfo));
        return String.valueOf(chars).replace("\\\\", "");
    }

}
