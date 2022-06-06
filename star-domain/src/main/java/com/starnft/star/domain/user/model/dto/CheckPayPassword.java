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
public class CheckPayPassword {

    private String token;

    private Long userId;

    private String payPassword;
}
