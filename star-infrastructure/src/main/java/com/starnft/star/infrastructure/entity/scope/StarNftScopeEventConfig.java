package com.starnft.star.infrastructure.entity.scope;

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

@ApiModel(value="com-starnft-star-infrastructure-entity-scope-StarNftScopeEventConfig")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_scope_event_config")
public class StarNftScopeEventConfig implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 事件名称
     */
    @TableField(value = "event_name")
    @ApiModelProperty(value="事件名称")
    private String eventName;

    /**
     * 事件编号
     */
    @TableField(value = "event_code")
    @ApiModelProperty(value="事件编号")
    private Integer eventCode;

    /**
     * 事件描述
     */
    @TableField(value = "event_desc")
    @ApiModelProperty(value="事件描述")
    private String eventDesc;

    /**
     * 事件状态
     */
    @TableField(value = "event_status")
    @ApiModelProperty(value="事件状态")
    private Integer eventStatus;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    @ApiModelProperty(value="是否删除")
    private Boolean isDeleted;
    @TableField(value = "scope_type")
    @ApiModelProperty(value="积分类型")
    private Integer scopeType;
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

    public static final String COL_EVENT_NAME = "event_name";

    public static final String COL_EVENT_CODE = "event_code";

    public static final String COL_EVENT_DESC = "event_desc";

    public static final String COL_EVENT_STATUS = "event_status";

    public static final String COL_IS_DELETED = "is_deleted";

    public static final String COL_CREATED_AT = "created_at";

    public static final String COL_MODIFIED_AT = "modified_at";
}