package com.starnft.star.infrastructure.entity.bulletin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 盯链公告DO
 */
@Data
@TableName("star_ding_bulletin")
public class StarDingBulletin {
    // 注释继承BaseEntity，因为表字段和BaseEntity属性不一致，以表字段为准
    // extends BaseEntity

    @TableId
    private Long id;

    private String title;

    private String image;

    private String link;

    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("created_at")
    private Date createdAt;

    @TableField("created_by")
    private Long createdBy;

    private Date updatedAt;

    private Long updatedBy;

}
