package com.starnft.star.domain.activity.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ActivityVO {
    /**
     * 商家id
     */
    @ApiModelProperty(name = "商家id", notes = "")
    private Integer sellerId;
    /**
     * spu id
     */
    @ApiModelProperty(name = "spu id", notes = "")
    private Long spuId;
    /**
     * sku id
     */
    @ApiModelProperty(name = "sku id", notes = "")
    private Long skuId;
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
     * 冻结库存
     */
    @ApiModelProperty(name = "冻结库存", notes = "")
    private Integer frozenStock;
    /**
     * 已卖库存
     */
    @ApiModelProperty(name = "已卖库存", notes = "")
    private Integer soldStock;

    /**
     * 秒杀定价
     */
    @ApiModelProperty(name = "秒杀定价", notes = "")
    private BigDecimal secCost;
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
}
