package com.starnft.star.infrastructure.entity.number;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品流转历史表，包括商品的铸造（发行），寄售，取消寄售，用户买入等
 *
 * @TableName star_nft_number_circulation_hist
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_number_circulation_hist")
public class StarNftNumberCirculationHist implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 主题编号id
     */
    private Long numberId;

    /**
     * 流转类型 0：铸造  1：寄售  2：取消寄售  3：买入
     */
    private Integer type;

    /**
     * 变化前价格
     */
    private BigDecimal beforePrice;

    /**
     * 变化后价格
     */
    private BigDecimal afterPrice;

    /**
     * 变化前物主
     */
    private String beforeOwner;

    /**
     * 变化后物主
     */
    private String afterOwner;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否删除(0:未删除 1:已删除)
     */
    private Boolean isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}