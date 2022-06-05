package com.starnft.star.application.process.wallet.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@ApiModel("交易记录响应")
public class TransactionRecord implements Serializable {

    @ApiModelProperty(value = "userid")
    private Long userId;
    /**
     * 平台交易流水号
     */
    @ApiModelProperty(value = "平台交易流水号")
    private String transactionSn;
    /**
     * 第三方交易流水号
     */
    @ApiModelProperty(value = "第三方交易流水号")
    private String outTradeNo;
    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderSn;
    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal money;
    /**
     * 支付渠道
     */
    @ApiModelProperty(value = "支付渠道")
    private String channel;
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型")
    private String payType;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;
    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态")
    private String status;

}
