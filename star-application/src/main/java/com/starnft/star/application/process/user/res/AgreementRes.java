package com.starnft.star.application.process.user.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WeiChunLAI
 * @date 2022/5/13 10:49
 */
@Data
@Accessors(chain = true)
public class AgreementRes {

    @ApiModelProperty("协议内容")
    private String agreementContent;

    @ApiModelProperty("协议名称")
    private String agreementName;

    @ApiModelProperty("协议版本")
    private String agreementVersion;

    @ApiModelProperty("协议类型")
    private String agreementType;

    @ApiModelProperty("协议场景")
    private String agreementScene;
}
