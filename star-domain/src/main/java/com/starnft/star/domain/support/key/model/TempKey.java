package com.starnft.star.domain.support.key.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ApiModel("临时秘钥实体")
public class TempKey implements Serializable {

    @ApiModelProperty("SecretId")
    private String tmpSecretId;
    @ApiModelProperty("SecretKey")
    private String tmpSecretKey;
    @ApiModelProperty("sessionToken")
    private String sessionToken;

}
