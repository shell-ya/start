package com.starnft.star.domain.wallet.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Builder
@ApiModel("绑卡申请请求")
public class CardBindReq implements Serializable {
    /**
     * uid
     */
    @ApiModelProperty(value = "uid")
    private Long uid;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号", required = true)
    @NotNull(message = "银行卡号不能为空")
    @Pattern(regexp = "[1-9]\\d*", message = "卡号输入有误")
    private String cardNo;
    /**
     * 卡类型
     */
    @ApiModelProperty(value = "卡类型")
    private String cardType;
    /**
     * 持卡人姓名
     */
    @ApiModelProperty(value = "持卡人姓名", required = true)
    @NotBlank(message = "持卡人姓名不能为空")
    private String cardName;
    /**
     * 银行缩写
     */
    @ApiModelProperty(value = "银行缩写")
    private String bankShortName;

    /**
     * 银行预留电话
     */
    @NotBlank(message = "银行预留电话不能为空")
    @ApiModelProperty(value = "银行预留电话", required = true)
    @Pattern(regexp = "0?(13|14|15|18|17)[0-9]{9}", message = "手机号格式错误")
    private String phone;

    /**
     * 是否默认卡 无卡绑定自动设为默认
     */
    @ApiModelProperty(value = "是否默认卡 无卡绑定自动设为默认")
    private Integer isDefault;
}
