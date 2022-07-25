package com.starnft.star.infrastructure.entity.compose;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_compose_category")
public class StarNftComposeCategory implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 合成id
     */
    @TableField(value = "compose_id")
    private Long composeId;

    /**
     * 合成材料
     */
    @TableField(value = "compose_material")
    private String composeMaterial;

    /**
     * 是否需要消耗积分
     */
    @TableField(value = "is_score")
    private Boolean isScore;

    /**
     * 积分类型
     */
    @TableField(value = "compose_scope_type")
    private Integer composeScopeType;

    /**
     * 积分数量
     */
    @TableField(value = "compose_scope_number")
    private Integer composeScopeNumber;
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_COMPOSE_ID = "compose_id";

    public static final String COL_COMPOSE_MATERIAL = "compose_material";

    public static final String COL_IS_SCORE = "is_score";

    public static final String COL_COMPOSE_SCOPE_TYPE = "compose_scope_type";

    public static final String COL_COMPOSE_SCOPE_NUMBER = "compose_scope_number";
    public static final String COL_IS_DELETED= "is_deleted";
}