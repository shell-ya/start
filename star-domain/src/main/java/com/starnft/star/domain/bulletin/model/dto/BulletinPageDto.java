package com.starnft.star.domain.bulletin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2022/7/25 6:50 PM
 * @Author ： shellya
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulletinPageDto {

    private Integer page;
    private Integer size;
}
