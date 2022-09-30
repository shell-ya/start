package com.starnft.star.business.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starnft.star.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "白名单配置表", description = "")
@TableName("white_list_config")
public class WhiteListConfig implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @TableId
    private Long id;
    /**
     * 白名单描述
     */
    @ApiModelProperty(name = "白名单描述", notes = "")
    private String whiteDesc;
    /**
     * 白名单类型
     */
    @ApiModelProperty(name = "白名单类型", notes = "")
    private Integer whiteType;
    /**
     * 是否生效
     */
    @ApiModelProperty(name = "是否生效", notes = "")
    private Integer effective;

    /**
     * 商品id
     */
    @ApiModelProperty(name = "商品id", notes = "")
    private Long goodsId;

    /**
     * 有效开始时间
     */
    @ApiModelProperty(name = "有效开始时间", notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date startTime;

    /**
     * 有效结束时间
     */
    @ApiModelProperty(name = "有效结束时间", notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date endTime;

    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("created_at")
    private Date createdAt;

    @TableField("created_by")
    private Long createdBy;

    @TableField("modified_at")
    private Date modifiedAt;

    @TableField("modified_by")
    private Long modifiedBy;


}
