package com.starnft.star.infrastructure.entity.theme;

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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_theme_info")
public class StarNftThemeInfo implements Serializable {
    /**
     * 主键id
     */

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 系列id
     */
    @TableField(value = "series_id")
    private Long seriesId;

    /**
     * 主题类型(1-藏品 2-盲盒)
     */
    @TableField(value = "theme_type")
    private Integer themeType;

    /**
     * 主题名称
     */
    @TableField(value = "theme_name")
    private String themeName;

    /**
     * 主题图片
     */
    @TableField(value = "theme_pic")
    private String themePic;

    /**
     * 发行数量
     */
    @TableField(value = "publish_number")
    private Integer publishNumber;

    /**
     * 主题描述
     */
    @TableField(value = "descrption")
    private String descrption;

    /**
     * 主题级别
     */
    @TableField(value = "theme_level")
    private Byte themeLevel;

    /**
     * 库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 发行价格
     */
    @TableField(value = "lssue_price")
    private BigDecimal lssuePrice;

    /**
     * 创建时间
     */
    @TableField(value = "create_at")
    private Date createAt;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_at")
    private Date updateAt;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 是否删除(0:未删除 1:已删除)
     */
    @TableField(value = "is_delete")
    private Boolean isDelete;

    /**
     * 是否推荐
     */
    @TableField(value = "is_recommend")
    private Boolean isRecommend;

    /**
     * 限制数量
     */
    @TableField(value = "restrict_number")
    private Integer restrictNumber;

    /**
     * 标记
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 合约地址
     */
    @TableField(value = "contract_address")
    private String contractAddress;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_SERIES_ID = "series_id";

    public static final String COL_THEME_TYPE = "theme_type";

    public static final String COL_THEME_NAME = "theme_name";

    public static final String COL_THEME_PIC = "theme_pic";

    public static final String COL_PUBLISH_NUMBER = "publish_number";

    public static final String COL_DESCRPTION = "descrption";

    public static final String COL_THEME_LEVEL = "theme_level";

    public static final String COL_STOCK = "stock";

    public static final String COL_LSSUE_PRICE = "lssue_price";

    public static final String COL_CREATE_AT = "create_at";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_AT = "update_at";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_IS_DELETE = "is_delete";

    public static final String COL_IS_RECOMMEND = "is_recommend";

    public static final String COL_RESTRICT_NUMBER = "restrict_number";

    public static final String COL_TAGS = "tags";

    public static final String COL_CONTRACT_ADDRESS = "contract_address";
}