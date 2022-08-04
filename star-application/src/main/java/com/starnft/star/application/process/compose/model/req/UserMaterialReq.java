package com.starnft.star.application.process.compose.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMaterialReq implements Serializable {
    @ApiModelProperty("类目ID")
    private Long  categoryId;
    @ApiModelProperty("主题ID")
    private Long themeId;
}
