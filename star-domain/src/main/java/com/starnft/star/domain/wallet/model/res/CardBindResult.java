package com.starnft.star.domain.wallet.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ApiModel("绑卡信息")
public class CardBindResult implements Serializable {
    /**
     * 卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private String cardNo;
    /**
     * 持卡人姓名
     */
    @ApiModelProperty(value = "持卡人姓名")
    private String cardName;
    /**
     * 银行简称
     */
    @ApiModelProperty(value = "银行简称")
    private String bankShortName;

}
