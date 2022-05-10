package com.starnft.star.domain.user.model.dto;

import lombok.Data;

/**
 * @author WeiChunLAI
 */
@Data
public class CheckPayPassword {

    private Long userId;

    private String payPassword;
}
