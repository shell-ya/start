package com.starnft.star.domain.wallet.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class BankRelationVO implements Serializable {
    /**
     * uid
     */
    private Long uid;
    /**
     * 用户昵称
     */
    private String Nickname;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 持卡人姓名
     */
    private String cardName;
    /**
     * 银行名称缩写
     */
    private String bankShortName;
}
