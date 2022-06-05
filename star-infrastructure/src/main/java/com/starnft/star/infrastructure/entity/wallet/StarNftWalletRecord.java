package com.starnft.star.infrastructure.entity.wallet;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("star_nft_wallet_record")
public class StarNftWalletRecord extends BaseEntity implements Serializable {
    /** id */
    @ApiModelProperty(name = "id",notes = "")
    @TableId
    private Long id ;
    /** 交易流水号 */
    @ApiModelProperty(name = "交易流水号",notes = "")
    private String recordSn ;
    /** 订单号 */
    @ApiModelProperty(name = "订单号",notes = "")
    private String orderSn;
    /** 第三方交易流水号 */
    @ApiModelProperty(name = "第三方交易流水号",notes = "")
    private String outTradeNo;
    /** 支付方uid 0系统充值 -1 系统退款 */
    @ApiModelProperty(name = "支付方uid 0系统充值 -1 系统退款",notes = "")
    private Long fromUid ;
    /** 接收方uid 0系统提现 */
    @ApiModelProperty(name = "接收方uid 0系统提现",notes = "")
    private Long toUid ;
    /** 交易类型 1充值 2提现 3交易 4退款 */
    @ApiModelProperty(name = "交易类型 1充值 2提现 3交易 4退款",notes = "")
    private Integer tsType ;
    /** 交易金额 */
    @ApiModelProperty(name = "交易金额",notes = "")
    private BigDecimal tsMoney ;
    /** 支付渠道 0未知 1支付宝 2微信 3银行卡 4余额 */
    @ApiModelProperty(name = "支付渠道 0未知 1支付宝 2微信 3银行卡 4余额",notes = "")
    private String payChannel ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
    /** 支付状态 待支付 失败 成功 支付中 支付关闭 */
    @ApiModelProperty(name = "支付状态 待支付 失败 成功 支付中 支付关闭",notes = "")
    private String payStatus ;
    /** 交易时间 */
    @ApiModelProperty(name = "交易时间",notes = "")
    private Date payTime ;

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

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Long getFromUid() {
        return fromUid;
    }

    public void setFromUid(Long fromUid) {
        this.fromUid = fromUid;
    }

    public Long getToUid() {
        return toUid;
    }

    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    public Integer getTsType() {
        return tsType;
    }

    public void setTsType(Integer tsType) {
        this.tsType = tsType;
    }

    public BigDecimal getTsMoney() {
        return tsMoney;
    }

    public void setTsMoney(BigDecimal tsMoney) {
        this.tsMoney = tsMoney;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }


}
