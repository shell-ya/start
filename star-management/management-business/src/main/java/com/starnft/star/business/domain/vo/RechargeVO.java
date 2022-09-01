package com.starnft.star.business.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Date 2022/9/1 12:04 PM
 * @Author ： shellya
 */
@Data
public class RechargeVO {

    @NotBlank(message = "uid不能为空")
    @ApiModelProperty(value = "用户id", required = true)
    private String uid;

//    @NotBlank(message = "orderSn不能为空")
    @ApiModelProperty(value = "交易流水号", required = false)
    private String orderSn;
//
//    @NotBlank(message = "transSn不能为空")
//    private String transSn;

    @NotBlank(message = "totalAmount不能为空")
    @ApiModelProperty(value = "付款金额", required = true)
    private BigDecimal totalAmount;

//    @NotBlank(message = "渠道不能为空")
    @ApiModelProperty(value = "渠道", required = false)
    private String payChannel;

    /**
     * 当前余额
     */
    @ApiModelProperty(name = "当前余额", notes = "")
    private BigDecimal currMoney;


    /**
     * 当前余额
     */
    @ApiModelProperty(name = "奖品", notes = "")
    private String  awardName;

    @ApiModelProperty(name = "是否核销 1：用户中奖现金 2:幸运用户" ,required = true)
    private Integer verification;

}
