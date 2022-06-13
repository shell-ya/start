package com.starnft.star.infrastructure.entity.publisher;

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

@ApiModel(value="com-starnft-star-infrastructure-entity-publisher-StarNftPublisher")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_publisher")
public class StarNftPublisher implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "publisher")
    @ApiModelProperty(value="")
    private String publisher;

    @TableField(value = "is_base")
    @ApiModelProperty(value="")
    private Boolean isBase;

    @TableField(value = "pic")
    @ApiModelProperty(value="")
    private String pic;

    @TableField(value = "pid")
    @ApiModelProperty(value="")
    private Long pid;

    @TableField(value = "created_at")
    @ApiModelProperty(value="")
    private Date createdAt;

    @TableField(value = "modified_at")
    @ApiModelProperty(value="")
    private Date modifiedAt;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PUBLISHER = "publisher";

    public static final String COL_IS_BASE = "is_base";

    public static final String COL_PIC = "pic";

    public static final String COL_PID = "pid";

    public static final String COL_CREATED_AT = "created_at";

    public static final String COL_MODIFIED_AT = "modified_at";
}