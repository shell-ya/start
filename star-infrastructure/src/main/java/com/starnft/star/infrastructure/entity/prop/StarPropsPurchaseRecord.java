package com.starnft.star.infrastructure.entity.prop;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 道具购买记录表;
 *
 * @author : http://www.chiner.pro
 * @date : 2022-7-24
 */
@ApiModel(value = "道具购买记录表", description = "")
@TableName("star_props_purchase_record")
public class StarPropsPurchaseRecord extends BaseEntity implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @TableId
    private Long id;
    /**
     * user id
     */
    @ApiModelProperty(name = "user id", notes = "")
    private Long uid;
    /**
     * 道具id
     */
    @ApiModelProperty(name = "道具id", notes = "")
    private Long propId;
    /**
     * 道具订单号
     */
    @ApiModelProperty(name = "道具订单号", notes = "")
    private String orderSn;
    /**
     * 购买数量
     */
    @ApiModelProperty(name = "购买数量", notes = "")
    private Integer count;
    /**
     * 商品单价
     */
    @ApiModelProperty(name = "商品单价", notes = "")
    private BigDecimal unitCost;
    /**
     * 优惠价格
     */
    @ApiModelProperty(name = "优惠价格", notes = "")
    private BigDecimal discounts;
    /**
     * 优惠渠道
     */
    @ApiModelProperty(name = "优惠渠道", notes = "")
    private String discountsChannel;
    /**
     * 订单总价
     */
    @ApiModelProperty(name = "订单总价", notes = "")
    private BigDecimal orderCost;
    /**
     * 订单状态
     */
    @ApiModelProperty(name = "订单状态", notes = "")
    private Integer status;

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

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getDiscounts() {
        return discounts;
    }

    public void setDiscounts(BigDecimal discounts) {
        this.discounts = discounts;
    }

    public String getDiscountsChannel() {
        return discountsChannel;
    }

    public void setDiscountsChannel(String discountsChannel) {
        this.discountsChannel = discountsChannel;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
