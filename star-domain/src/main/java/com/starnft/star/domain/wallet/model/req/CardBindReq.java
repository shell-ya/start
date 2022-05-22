package com.starnft.star.domain.wallet.model.req;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
public class CardBindReq implements Serializable {
    /**
     * uid
     */
    private Long uid;

    /**
     * 昵称
     */
    private String Nickname;

    /**
     * 银行卡号
     */
    @NotNull(message = "银行卡号不能为空")
    @Max(value = 19, message = "长度不能大于19位")
    @Min(value = 13, message = "长度不能小于13")
    private Long cardNo;
    /**
     * 持卡人姓名
     */
    @NotBlank(message = "持卡人姓名不能为空")
    private String cardName;
    /**
     * 银行缩写
     */
    private String bankShortName;
}
