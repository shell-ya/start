package com.starnft.star.business.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author WeiChunLAI
 */
@Data
public class BussinessBaseEntity {

    @TableField( "is_deleted")
    private Boolean isDeleted;

    @TableField( "created_at")
    private Date createdAt;

    @TableField( "created_by")
    private Long createdBy;

    @TableField( "modified_at")
    private Date  modifiedAt;

    @TableField("modified_by")
    private Long modifiedBy;
}
