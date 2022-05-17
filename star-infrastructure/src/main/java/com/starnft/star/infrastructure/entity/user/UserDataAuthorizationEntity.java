package com.starnft.star.infrastructure.entity.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class UserDataAuthorizationEntity extends BaseEntity implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    private LocalDateTime authorizationEndTime;

    private Long authorizationId;

    private LocalDateTime authorizationStartTime;

    private static final long serialVersionUID = 1L;
}