package com.starnft.star.application.process.wallet.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.common.annotations.Desensitized;
import com.starnft.star.common.enums.SensitiveTypeEnum;
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
    @JsonSerialize(using = ToStringSerializer.class)
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
    @ApiModelProperty(value = "支付类型 1充值 2提现 3退款 4买入 5卖出")
    private String payType;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date payTime;
    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态")
    private String status;
    /**
     * 卡号
     */
    @ApiModelProperty(value = "脱敏卡号")
    @Desensitized(type = SensitiveTypeEnum.BANK_CARD)
    private String cardNo;
    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因")
    private String applyMsg;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private String currMoney;

}
