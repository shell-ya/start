package com.starnft.star.infrastructure.entity.activityEvent;

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
import java.util.Date;

@ApiModel(value="com-starnft-star-infrastructure-entity-activityEvent-StarNftActivity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_activity")
public class StarNftActivity implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 活动标记
     */
    @TableField(value = "activity_sign")
    @ApiModelProperty(value="活动标记")
    private String activitySign;

    /**
     * 活动名称
     */
    @TableField(value = "activity_name")
    @ApiModelProperty(value="活动名称")
    private String activityName;

    /**
     * 活动开始时间
     */
    @TableField(value = "start_time")
    @ApiModelProperty(value="活动开始时间")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @TableField(value = "end_time")
    @ApiModelProperty(value="活动结束时间")
    private Date endTime;

    /**
     * 是否有时间限制
     */
    @TableField(value = "is_times")
    @ApiModelProperty(value="是否有时间限制")
    private Boolean isTimes;

    /**
     * 活动状态
     */
    @TableField(value = "activity_status")
    @ApiModelProperty(value="活动状态")
    private Integer activityStatus;

    /**
     * 活动说明
     */
    @TableField(value = "activity_content")
    @ApiModelProperty(value="活动说明")
    private String activityContent;

    /**
     * 附加参数
     */
    @TableField(value = "extend")
    @ApiModelProperty(value="附加参数")
    private String extend;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    @ApiModelProperty(value="创建时间")
    private Date createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "modified_at")
    @ApiModelProperty(value="修改时间")
    private Date modifiedAt;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ACTIVITY_SIGN = "activity_sign";

    public static final String COL_ACTIVITY_NAME = "activity_name";

    public static final String COL_START_TIME = "start_time";

    public static final String COL_END_TIME = "end_time";

    public static final String COL_IS_TIMES = "is_times";

    public static final String COL_ACTIVITY_STATUS = "activity_status";

    public static final String COL_ACTIVITY_CONTENT = "activity_content";

    public static final String COL_EXTEND = "extend";

    public static final String COL_CREATED_AT = "created_at";

    public static final String COL_MODIFIED_AT = "modified_at";
}