package com.starnft.star.infrastructure.entity.wallet;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 银行卡绑定表;
 *
 * @author :Ryan z
 * @date : 2022-5-22
 */
@ApiModel(value = "银行卡绑定表", description = "")
@TableName("star_nft_bank_relation")
public class StarNftBankRelation extends BaseEntity implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @TableId
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty(name = "用户id", notes = "")
    private Long uid;
    /**
     * 用户名称
     */
    @ApiModelProperty(name = "用户名称", notes = "")
    private String nickname;
    /**
     * 银行卡号
     */
    @ApiModelProperty(name = "银行卡号", notes = "")
    private String cardNo;
    /**
     * 卡类型
     */
    @ApiModelProperty(name = "卡类型", notes = "")
    private String cardType;
    /**
     * 持卡人姓名
     */
    @ApiModelProperty(name = "持卡人姓名", notes = "")
    private String cardName;
    /**
     * 开户银行简称
     */
    @ApiModelProperty(name = "开户银行简称", notes = "")
    private String bankNameShort;
    /**
     * 银行预留手机号
     */
    @ApiModelProperty(name = "银行预留手机号", notes = "")
    private String phone;
    /**
     * 是否是默认卡
     */
    @ApiModelProperty(name = "是否是默认卡", notes = "")
    private Integer isDefault;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getBankNameShort() {
        return bankNameShort;
    }

    public void setBankNameShort(String bankNameShort) {
        this.bankNameShort = bankNameShort;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}
