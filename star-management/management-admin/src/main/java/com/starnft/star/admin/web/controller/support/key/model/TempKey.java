package com.starnft.star.admin.web.controller.support.key.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Date 2022/6/22 10:27 AM
 * @Author ： shellya
 */
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
    private long startTime;
    private long expiredTime;
    private String bucket;

}
