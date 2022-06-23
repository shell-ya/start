package com.starnft.star.infrastructure.entity.scope;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-starnft-star-infrastructure-entity-scope-StarNftScopeRecord")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_scope_record")
public class StarNftScopeRecord implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Long id;

    @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Long userId;

    @TableField(value = "`scope`")
    @ApiModelProperty(value="")
    private BigDecimal scope;

    @TableField(value = "mold")
    @ApiModelProperty(value="")
    private Integer mold;

    @TableField(value = "remarks")
    @ApiModelProperty(value="")
    private String remarks;

    @TableField(value = "created_at")
    @ApiModelProperty(value="")
    private Date createdAt;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_SCOPE = "scope";

    public static final String COL_MOLD = "mold";

    public static final String COL_REMARKS = "remarks";

    public static final String COL_CREATED_AT = "created_at";
}