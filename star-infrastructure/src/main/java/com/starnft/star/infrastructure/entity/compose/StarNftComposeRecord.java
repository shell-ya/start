package com.starnft.star.infrastructure.entity.compose;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_compose_record")
public class StarNftComposeRecord implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "compose_id")
    private Long composeId;

    @TableField(value = "category_id")
    private Long categoryId;

    @TableField(value = "`source`")
    private String source;

    @TableField(value = "is_scope")
    private Boolean isScope;

    @TableField(value = "scope_number")
    private Integer scopeNumber;

    @TableField(value = "prize_type")
    private Integer prizeType;

    @TableField(value = "prize_message")
    private String prizeMessage;

    @TableField(value = "created_at")
    private Date createdAt;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_COMPOSE_ID = "compose_id";

    public static final String COL_CATEGORY_ID = "category_id";

    public static final String COL_SOURCE = "source";

    public static final String COL_IS_SCOPE = "is_scope";

    public static final String COL_SCOPE_NUMBER = "scope_number";

    public static final String COL_PRIZE_TYPE = "prize_type";

    public static final String COL_PRIZE_MESSAGE = "prize_message";

    public static final String COL_CREATED_AT = "created_at";
}