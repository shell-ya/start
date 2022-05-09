package com.starnft.star.infrastructure.entity.wallet;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("star_nft_wallet_log")
public class StarNftWalletLog extends BaseEntity implements Serializable {

    /** id */
    @ApiModelProperty(name = "id",notes = "")
    @TableId
    private Long id ;
    /** 交易流水号 */
    @ApiModelProperty(name = "交易流水号",notes = "")
    private String recordSn ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Long uid ;
    /** 钱包id */
    @ApiModelProperty(name = "钱包id",notes = "")
    private String wId ;
    /** 变动金额 增+ 减- */
    @ApiModelProperty(name = "变动金额 增+ 减-",notes = "")
    private BigDecimal balanceOffset ;
    /** 变动后的金额 */
    @ApiModelProperty(name = "变动后的金额",notes = "")
    private BigDecimal currentBalance ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
    /** 是否显示 0不显示 1显示 */
    @ApiModelProperty(name = "是否显示 0不显示 1显示",notes = "")
    private Integer display ;

    @ApiModelProperty(name = "渠道",notes = "")
    private String channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordSn() {
        return recordSn;
    }

    public void setRecordSn(String recordSn) {
        this.recordSn = recordSn;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId;
    }

    public BigDecimal getBalanceOffset() {
        return balanceOffset;
    }

    public void setBalanceOffset(BigDecimal balanceOffset) {
        this.balanceOffset = balanceOffset;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
