package com.starnft.star.domain.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WeiChunLAI
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserVerifyCode {

    private String code;
}
