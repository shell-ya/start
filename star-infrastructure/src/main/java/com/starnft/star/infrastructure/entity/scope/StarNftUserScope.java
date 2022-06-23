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
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="com-starnft-star-infrastructure-entity-scope-StarNftUserScope")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_user_scope")
public class StarNftUserScope implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 用户积分
     */
    @TableField(value = "user_scope")
    @ApiModelProperty(value="用户积分")
    private BigDecimal userScope;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    @ApiModelProperty(value="创建时间")
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "modified_at")
    @ApiModelProperty(value="更新时间")
    private Date modifiedAt;
    @TableField(value = "version")
    @ApiModelProperty(value="版本号")
    private Integer version;
    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USER_SCOPE = "user_scope";

    public static final String COL_CREATED_AT = "created_at";

    public static final String COL_MODIFIED_AT = "modified_at";
    public static final String COL_VERSION = "version";
}