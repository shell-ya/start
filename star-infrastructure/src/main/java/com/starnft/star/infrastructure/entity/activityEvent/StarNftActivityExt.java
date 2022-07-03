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

@ApiModel(value="com-starnft-star-infrastructure-entity-activityEvent-StarNftActivityExt")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_activity_ext")
public class StarNftActivityExt implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "ext_type")
    @ApiModelProperty(value="")
    private Integer extType;

    @TableField(value = "activity_sign")
    @ApiModelProperty(value="")
    private String activitySign;
    @TableField(value = "event_sign")
    @ApiModelProperty(value="")
    private String eventSign;

    @TableField(value = "activity_id")
    @ApiModelProperty(value="")
    private Long activityId;

    @TableField(value = "params")
    @ApiModelProperty(value="")
    private String params;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_EXT_TYPE = "ext_type";

    public static final String COL_ACTIVITY_SIGN = "activity_sign";
    public static final String COL_EVENT_SIGN = "event_sign";

    public static final String COL_ACTIVITY_ID = "activity_id";

    public static final String COL_PARAMS = "params";
}