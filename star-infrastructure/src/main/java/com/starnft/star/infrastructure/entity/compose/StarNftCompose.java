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
@TableName(value = "star_nft_compose")
public class StarNftCompose implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 合成标题
     */
    @TableField(value = "compose_name")
    private String composeName;

    /**
     * 标题图片
     */
    @TableField(value = "compose_images")
    private String composeImages;

    /**
     * 开始时间
     */
    @TableField(value = "started_at")
    private Date startedAt;

    /**
     * 结束时间
     */
    @TableField(value = "end_at")
    private Date endAt;

    /**
     * 状态
     */
    @TableField(value = "compose_status")
    private Integer composeStatus;

    /**
     * 描述
     */
    @TableField(value = "compose_remark")
    private String composeRemark;
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_COMPOSE_NAME = "compose_name";

    public static final String COL_COMPOSE_IMAGES = "compose_images";

    public static final String COL_STARTED_AT = "started_at";

    public static final String COL_END_AT = "end_at";

    public static final String COL_COMPOSE_STATUS = "compose_status";

    public static final String COL_COMPOSE_REMARK = "compose_remark";
    public static final String COL_IS_DELETED= "is_deleted";
}