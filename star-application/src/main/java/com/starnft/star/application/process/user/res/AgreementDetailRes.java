package com.starnft.star.application.process.user.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WeiChunLAI
 * @date 2022/5/17 20:13
 */
@Data
public class AgreementDetailRes {

    @ApiModelProperty("协议id")
    private String agreementId;

    @ApiModelProperty("协议名称")
    private String agreementName;

    @ApiModelProperty("协议场景")
    private String agreementScene;

    @ApiModelProperty("协议类型")
    private String agreementType;

    @ApiModelProperty("协议版本")
    private String agreementVersion;

    @ApiModelProperty("协议内容")
    private String agreementContent;
}
