package com.starnft.star.infrastructure.entity.coupon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 5:39 下午
 * @description： TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_coupon_history")
public class StarNftCouponHistory extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

  /**
     * 优惠卷id
     */
    @TableField(value = "coupon_id")
    private String couponId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 获取方式：example 1-空投 2-活动
     * 具体参考枚举
     */
    @TableField(value = "get_type")
    private Integer getType;

    /**
     * 使用状态 1-未使用 2-已使用
     */
    @TableField(value = "use_status")
    private Integer useStatus;

    /**
     * 使用时间
     */
    @TableField(value = "use_time")
    private Date useTime;

    /**
     * 订单id-在哪个订单使用的?
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 渠道id example: event id
     */
    @TableField(value = "channel_id")
    private String channelId;
}