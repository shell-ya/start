package com.starnft.star.infrastructure.entity.activity;

import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "秒杀活动表", description = "")
public class StarScheduleSeckill extends BaseEntity implements Serializable {
    /**
     * 商家id
     */
    @ApiModelProperty(name = "商家id", notes = "")
    private Integer sellerId;
    /**
     * spu id
     */
    @ApiModelProperty(name = "spu id", notes = "")
    private Integer spuId;
    /**
     * sku id
     */
    @ApiModelProperty(name = "sku id", notes = "")
    private Integer skuId;
    /**
     * 库存
     */
    @ApiModelProperty(name = "库存", notes = "")
    private Integer stock;
    /**
     * 秒杀商品数
     */
    @ApiModelProperty(name = "秒杀商品数", notes = "")
    private Integer goodsNum;
    /**
     * 秒杀定价
     */
    @ApiModelProperty(name = "秒杀定价", notes = "")
    private Double secCost;
    /**
     * 开始时间
     */
    @ApiModelProperty(name = "开始时间", notes = "")
    private Date startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(name = "结束时间", notes = "")
    private Date endTime;
    /**
     * 商品描述
     */
    @ApiModelProperty(name = "商品描述", notes = "")
    private String goodsDesc;
    /**
     * 审核状态 0未审核 1审核通过
     */
    @ApiModelProperty(name = "审核状态 0未审核 1审核通过", notes = "")
    private int status;

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getSecCost() {
        return secCost;
    }

    public void setSecCost(Double secCost) {
        this.secCost = secCost;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
