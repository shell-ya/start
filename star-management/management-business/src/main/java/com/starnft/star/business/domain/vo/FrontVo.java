package com.starnft.star.business.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Date 2022/6/10 1:14 AM
 * @Author ： shellya
 */
@Data
@Builder
public class FrontVo {

    //    @ApiModelProperty(value = "今日提现总数")
    private Long dayWithDrawApplyTotal;

    //    @ApiModelProperty(value = "昨天提现总数")
    private Long toDayWithDrawApplyTotal;

    //    @ApiModelProperty(value = "今日提现金额")
    private BigDecimal dayTotalWithdrawMoney;

    //    @ApiModelProperty(value = "昨日提现金额")
    private BigDecimal toDayTotalWithdrawMoney;

    //    @ApiModelProperty(value = "今日提现手续费")
    private BigDecimal dayTotalWithDrawRate;
    //    @ApiModelProperty(value = "昨日提现手续费")
    private BigDecimal toDayTotalWithDrawRate;

//    @ApiModelProperty(value = "今日充值总数")
    private Long dayPayRecordTotal;

//    @ApiModelProperty(value = "昨天充值总数")
    private Long toDayPayRecordTotal;

//    @ApiModelProperty(value = "今日充值金额")
    private BigDecimal dayTotalPayMoney;

//    @ApiModelProperty(value = "昨日充值金额")
    private BigDecimal toDayTotalPayMoney;

    //    @ApiModelProperty(value = "今日订单总数")
    private Long dayOrderTotal;

    //    @ApiModelProperty(value = "昨日订单总数")
    private Long toDayOrderTotal;

    //    @ApiModelProperty(value = "今日交易金额")
    private BigDecimal dayTotalOrderMoney;

    //    @ApiModelProperty(value = "昨日交易金额")
    private BigDecimal toDayTotalOrderMoney;

    //    @ApiModelProperty(value = "今日交易手续费")
    private BigDecimal dayTotalOrderRate;
    //    @ApiModelProperty(value = "昨日交易手续费")
    private BigDecimal toDayTotalOrderRate;

    private Integer allUserCount;
    private Integer dayUserCount;
    private Integer toDayUserCount;
}
