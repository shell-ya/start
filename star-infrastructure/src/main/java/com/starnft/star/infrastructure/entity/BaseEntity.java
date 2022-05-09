package com.starnft.star.infrastructure.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


import java.util.Date;

/**
 * @author WeiChunLAI
 */
@Data
public class BaseEntity {

    @TableField( "is_delete")
    private Boolean isDelete;

    @TableField( "created_at")
    private Date createdAt;

    @TableField( "created_by")
    private Long createdBy;

    @TableField( "modified_at")
    private Date  modifiedAt;

    @TableField("modified_by")
    private Long modifiedBy;
}
