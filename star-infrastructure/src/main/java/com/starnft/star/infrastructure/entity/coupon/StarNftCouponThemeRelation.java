package com.starnft.star.infrastructure.entity.coupon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/3 7:53 下午
 * @description： TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_coupon_theme_relation")
public class StarNftCouponThemeRelation extends BaseEntity {

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
     * 主题id
     */
    @TableField(value = "theme_id")
    private String themeId;

    /**
     * 主题名称
     */
    @TableField(value = "theme_name")
    private String themeName;
}
