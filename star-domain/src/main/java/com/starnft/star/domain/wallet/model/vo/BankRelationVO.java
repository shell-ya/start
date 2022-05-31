package com.starnft.star.domain.wallet.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Builder
@Data
@ApiModel("银行卡请求")
public class BankRelationVO implements Serializable {
    /**
     * uid
     */
    @ApiModelProperty(value = "uid")
    private Long uid;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String Nickname;
    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    @Pattern(regexp = "[1-9]\\d*", message = "卡号输入有误")
    private String cardNo;
    /**
     * 持卡人姓名
     */
    @ApiModelProperty(value = "持卡人姓名")
    private String cardName;
    /**
     * 银行名称缩写
     */
    @ApiModelProperty(value = "银行名称缩写")
    private String bankShortName;
    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认")
    private Integer isDefault;
    /**
     * 银行预留手机号
     */
    @JsonIgnore
    private String phone;
}
