package com.starnft.star.application.process.order.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("订单支付请求")
public class OrderPayReq implements Serializable {

    @ApiModelProperty(value = "uid", required = false)
    private Long userId;

    @ApiModelProperty(value = "商品拥有者id 发行产品有厂商ID 该字段传厂商id")
    @NotBlank(message = "oid could not be null")
    private String ownerId;

    @ApiModelProperty(value = "系列id")
    private Long seriesId;

    @ApiModelProperty(value = "藏品id")
    private Long numberId;

    @ApiModelProperty(value = "主题id")
    private Long themeId;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String orderSn;

    @ApiModelProperty(value = "第三方交易流水号")
    private String outTradeNo;

    @ApiModelProperty(value = "实付金额")
    private String payAmount;

    @ApiModelProperty(value = "总计金额")
    private String totalPayAmount;

    @ApiModelProperty(value = "交易手续费 根据平台手续费规则计算 总计金额-实付金额")
    private String fee;

    @ApiModelProperty(value = "交易类型 充值1 提现2 买入3 卖出4 退款5")
    private Integer type;

    @ApiModelProperty(value = "藏品类型 1藏品 2盲盒")
    private Integer categoryType;

    @ApiModelProperty(value = "交易渠道 支付宝 AliPay，云闪付 UNION_PAY，微信 WeChatPay，银行卡 BankCard，" +
            "快捷绑卡支付 QuickCard，余额 Balance 云账号 CloudAccount  市场交易 秒杀都仅能用余额支付，剩下的都是扩展预留")
    private String channel;

    @ApiModelProperty(value = "支付密码校验凭证")
    private String payToken;

    @ApiModelProperty(value = "返回路径")
    private String returnUri;

}
