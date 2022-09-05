package com.starnft.star.infrastructure.entity.coupon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 2:38 下午
 * @description： TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_coupon")
public class StarNftCoupon extends BaseEntity {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 优惠卷id
     */
    @TableField(value = "coupon_id")
    private String couponId;

    /**
     *  优惠卷类型
     *  参考枚举：CouponType.java
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 优惠卷名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 优惠卷图片
     */
    @TableField(value = "image")
    private String image;

    /**
     * 数量
     */
    @TableField(value = "count")
    private Integer count;

    /**
     * 优惠金额/折扣
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 每人限领几张
     */
    @TableField(value = "pre_limit")
    private Integer preLimit;

    /**
     * 使用门槛 example：0-无门槛
     */
    @TableField(value = "min_point")
    private Integer minPoint;

    /**
     * 有效期开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 有效期结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 使用类型 example：0-全场通用 1-指定系列 2-指定主题
     */
    @TableField(value = "use_type")
    private Integer useType;

    /**
     * 备注
     */
    @TableField(value = "note")
    private String note;

    /**
     * 发行数量
     */
    @TableField(value = "public_count")
    private Integer publicCount;

    /**
     * 使用数量
     */
    @TableField(value = "use_count")
    private Integer useCount;

    /**
     * 领取数量
     */
    @TableField(value = "receive_count")
    private Integer receiveCount;

    /**
     * 可以领取的日期
     */
    @TableField(value = "enable_time")
    private Date enableTime;
}
