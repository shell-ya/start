package com.starnft.star.domain.user.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author WeiChunLAI
 * @date 2022/5/16 21:58
 */
@Data
public class AgreementSignDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 协议id
     */
    private String agreementId;

    /**
     * 签署编号
     */
    private Long authorizationId;

    /**
     * 用户id
     */
    private Long userId;

    private Boolean isDeleted;

    private Date createdAt;

    private Long createdBy;

    private Date modifiedAt;

    private Long modifiedBy;
}
