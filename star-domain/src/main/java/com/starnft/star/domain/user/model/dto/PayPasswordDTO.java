package com.starnft.star.domain.user.model.dto;

import lombok.Data;

/**
 * @author WeiChunLAI
 */
@Data
public class PayPasswordDTO {

    private Long userId;

    private String payPassword;

    private String verificationCode;

    private String phone;

    private String token;
}
