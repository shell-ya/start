package com.starnft.star.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WeiChunLAI
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAddDTO {

    private Long userId;
    private String nickName;

    private Long createBy;

    private String phone;
}
