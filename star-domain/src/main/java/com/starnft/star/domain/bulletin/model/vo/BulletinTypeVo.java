package com.starnft.star.domain.bulletin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("公告类型")
@NoArgsConstructor
@AllArgsConstructor
public class BulletinTypeVo {

    @ApiModelProperty("类型值")
    private Integer type;
    @ApiModelProperty("类型描述")
    private String val;

}
