package com.starnft.star.infrastructure.entity.series;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_series")
public class StarNftSeries implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 系列名称
     */
    @TableField(value = "series_name")
    private String seriesName;

    /**
     * 系列类型(1-主题 2-盲盒)
     */
    @TableField(value = "series_type")
    private Boolean seriesType;

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
     * 是否删除(0-未删除 1-已删除)
     */
    @TableField(value = "is_delete")
    private Boolean isDelete;

    /**
     * 主题图片
     */
    @TableField(value = "series_images")
    private String seriesImages;

    /**
     * 模型地址
     */
    @TableField(value = "series_models")
    private String seriesModels;
    @TableField(value = "series_status")
    private Integer seriesStatus;
    private static final long serialVersionUID = 1L;
    public static final String COL_ID = "id";
    public static final String COL_SERIES_NAME = "series_name";
    public static final String COL_SERIES_TYPE = "series_type";

    public static final String COL_CREATE_AT = "create_at";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_AT = "update_at";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_IS_DELETE = "is_delete";

    public static final String COL_SERIES_IMAGES = "series_images";
    public static final String COL_SERIES_MODELS = "series_models";
    public static final String COL_SERIES_STATUS= "series_status";
}