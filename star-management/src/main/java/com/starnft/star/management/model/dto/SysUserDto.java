package com.starnft.star.management.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Date 2022/5/11 8:20 PM
 * @Author ： shellya
 */

@Data
@Builder
public class SysUserDto {

    private Long id;
    private Boolean status;
    private String  token;
}
