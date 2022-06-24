package com.starnft.star.infrastructure.entity.number;

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
 * 主题编号表
 */
@ApiModel(value = "com-starnft-star-infrastructure-entity-number-StarNftThemeNumber")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_theme_number")
public class StarNftThemeNumber implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 主题信息id
     */
    @TableField(value = "series_theme_info_id")
    @ApiModelProperty(value = "主题信息id")
    private Long seriesThemeInfoId;

    /**
     * 主题编号
     */
    @TableField(value = "theme_number")
    @ApiModelProperty(value = "主题编号")
    private Long themeNumber;

    /**
     * 合约地址
     */
    @TableField(value = "`contract_address`")
    @ApiModelProperty(value = "合约地址")
    private String contractAddress;

    /**
     * 链上标识
     */
    @TableField(value = "chain_identification")
    @ApiModelProperty(value = "链上标识")
    private String chainIdentification;

    /**
     * 价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 状态(0-未出售  1-已发行 2-已发售)
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态(0-发行未出售  1-发行已出售 2-未寄售 3-寄售中)")
    private Integer status;

    /**
     * 拥有者
     */
    @TableField(value = "owner_by")
    @ApiModelProperty(value = "拥有者")
    private String ownerBy;

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
     * 是否删除(0:未删除 1:已删除)
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value = "是否删除(0:未删除 1:已删除)")
    private Boolean isDelete;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_SERIES_THEME_INFO_ID = "series_theme_info_id";

    public static final String COL_THEME_NUMBER = "theme_number";

    public static final String COL_CONTRACT_ADDRESS = "contract_address";

    public static final String COL_CHAIN_IDENTIFICATION = "chain_identification";

    public static final String COL_PRICE = "price";

    public static final String COL_STATUS = "status";

    public static final String COL_OWENR_BY = "owenr_by";

    public static final String COL_CREATE_AT = "create_at";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_AT = "update_at";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_IS_DELETE = "is_delete";
}