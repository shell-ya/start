package com.starnft.star.infrastructure.entity.wallet;

import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;


import java.math.BigDecimal;

@TableName( "nft_wallet")
public class Wallet extends BaseEntity {

    /** id */
    @ApiModelProperty(name = "id",notes = "")
    @Id
    private Integer id ;
    /** 钱包id */
    @ApiModelProperty(name = "钱包id",notes = "")
    private String wId ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Long uid ;
    /** 余额 */
    @ApiModelProperty(name = "余额",notes = "")
    private BigDecimal balance ;
    /** 钱包总收入 */
    @ApiModelProperty(name = "钱包总收入",notes = "")
    private BigDecimal walletIncome ;
    /** 钱包总支出 */
    @ApiModelProperty(name = "钱包总支出",notes = "")
    private BigDecimal walletOutcome ;
    /** 是否被冻结 */
    @ApiModelProperty(name = "是否被冻结",notes = "")
    private String frozen ;
    /** 冻结金额 */
    @ApiModelProperty(name = "冻结金额",notes = "")
    private BigDecimal frozenFee ;
    /** id */
    public Integer getId(){
        return this.id;
    }
    /** id */
    public void setId(Integer id){
        this.id=id;
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getWalletIncome() {
        return walletIncome;
    }

    public void setWalletIncome(BigDecimal walletIncome) {
        this.walletIncome = walletIncome;
    }

    public BigDecimal getWalletOutcome() {
        return walletOutcome;
    }

    public void setWalletOutcome(BigDecimal walletOutcome) {
        this.walletOutcome = walletOutcome;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getFrozenFee() {
        return frozenFee;
    }

    public void setFrozenFee(BigDecimal frozenFee) {
        this.frozenFee = frozenFee;
    }
}
