package com.starnft.star.infrastructure.entity;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author WeiChunLAI
 */
@Data
public class BaseEntity {

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "modified_at")
    private Date  modifiedAt;

    @Column(name = "modified_by")
    private Long modifiedBy;
}
