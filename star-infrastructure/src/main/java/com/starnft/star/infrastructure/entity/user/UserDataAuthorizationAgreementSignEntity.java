package com.starnft.star.infrastructure.entity.user;

import java.io.Serializable;

import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class UserDataAuthorizationAgreementSignEntity extends BaseEntity implements Serializable {
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

    private static final long serialVersionUID = 1L;
}