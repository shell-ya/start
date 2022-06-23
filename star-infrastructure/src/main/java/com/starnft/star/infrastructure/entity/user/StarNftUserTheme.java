package com.starnft.star.infrastructure.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户藏品表
 */
@ApiModel(value = "com-starnft-star-infrastructure-entity-user-StarNftUserTheme")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_user_theme")
public class StarNftUserTheme implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 系列id
     */
    @TableField(value = "series_id")
    @ApiModelProperty(value = "系列id")
    private Long seriesId;;

    /**
     * 主题信息id
     */
    @TableField(value = "series_theme_info_id")
    @ApiModelProperty(value = "主题信息id")
    private Long seriesThemeInfoId;

    /**
     * 主题编号信息id
     */
    @TableField(value = "series_theme_id")
    @ApiModelProperty(value = "主题编号信息id")
    private Long seriesThemeId;

    /**
     * 状态(0-已购买 1-挂售中 2-已出售 )
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态(0-已购买 1-挂售中 2-已出售 )")
    private Integer status;

    /**
     * 拥有者
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "拥有者")
    private String userId;

    /**
     * 购买来源(1-藏品 2-盲盒)
     */
    @TableField(value = "`source`")
    @ApiModelProperty(value = "购买来源(1-藏品 2-盲盒)")
    private Integer source;

    /**
     * 税前价格
     */
    @TableField(value = "pre_tax_price")
    @ApiModelProperty(value = "税前价格")
    private BigDecimal preTaxPrice;

    /**
     * 平台税
     */
    @TableField(value = "platform_tax")
    @ApiModelProperty(value = "平台税")
    private BigDecimal platformTax;

    /**
     * 版权税
     */
    @TableField(value = "copyright_tax")
    @ApiModelProperty(value = "版权税")
    private BigDecimal copyrightTax;

    /**
     * 税后价格
     */
    @TableField(value = "after_tax_price")
    @ApiModelProperty(value = "税后价格")
    private BigDecimal afterTaxPrice;

    /**
     * 创建时间
     */
    @TableField(value = "create_at")
    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_at")
    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 是否删除(0-未删除 1-已删除)
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value = "是否删除(0-未删除 1-已删除)")
    private Boolean isDelete;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_SERIES_THEME_INFO_ID = "series_theme_info_id";

    public static final String COL_SERIES_THEME_ID = "series_theme_id";

    public static final String COL_STATUS = "status";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_SOURCE = "source";

    public static final String COL_PRE_TAX_PRICE = "pre_tax_price";

    public static final String COL_PLATFORM_TAX = "platform_tax";

    public static final String COL_COPYRIGHT_TAX = "copyright_tax";

    public static final String COL_AFTER_TAX_PRICE = "after_tax_price";

    public static final String COL_CREATE_AT = "create_at";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_AT = "update_at";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_IS_DELETE = "is_delete";
}