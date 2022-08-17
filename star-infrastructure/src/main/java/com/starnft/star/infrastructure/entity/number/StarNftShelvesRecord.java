package com.starnft.star.infrastructure.entity.number;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Date 2022/8/11 1:09 PM
 * @Author ： shellya
 */
@Data
@TableName("star_nft_shelves_record")
public class StarNftShelvesRecord extends BaseEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;


    /**
     * 用户钱包地址
     */
    @TableField(value = "wallet_hash")
    private String walletHash;

    /**
     * 藏品id
     */
    @TableField(value = "series_theme_id")
    private Long seriesThemeId;

    /**
     * 藏品hash
     */
    @TableField(value = "series_theme_hash")
    private String seriesThemeHash;

    /**
     * 合约地址
     */
    @TableField(value = "contract_address")
    private String contractAddress;

    /**
     * 价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 上架状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}
