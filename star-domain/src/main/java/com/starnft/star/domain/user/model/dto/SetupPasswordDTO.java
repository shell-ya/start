package com.starnft.star.domain.user.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Harlan
 * @date 2022/06/15 23:45
 */
@Data
@Builder
public class SetupPasswordDTO {
    private Long uid;
    private String password;
}
