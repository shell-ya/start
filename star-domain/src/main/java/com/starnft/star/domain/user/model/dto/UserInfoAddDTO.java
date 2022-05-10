package com.starnft.star.domain.user.model.dto;

import lombok.Data;

/**
 * @author WeiChunLAI
 */
@Data
public class UserInfoAddDTO {

    private Long userId;

    private String nickName;

    private Long createBy;

    private String phone;
}
