package com.starnft.star.domain.bulletin.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Date 2022/7/25 6:50 PM
 * @Author ： shellya
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulletinPageDto implements Serializable {

    @ApiModelProperty(value = "page")
    private Integer page;
    @ApiModelProperty(value = "size")
    private Integer size;
    @ApiModelProperty(value = "公告类型",hidden = true)
    private Integer bulletinType;
}
