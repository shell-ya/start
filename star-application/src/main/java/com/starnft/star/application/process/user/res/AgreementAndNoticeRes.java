package com.starnft.star.application.process.user.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WeiChunLAI
 * @date 2022/5/17 20:10
 */
@Data
@Accessors(chain = true)
public class AgreementAndNoticeRes {

    @ApiModelProperty("协议内容")
    private List<AgreementDetailRes> agreementDetailResList;

    @ApiModelProperty("弹窗信息")
    private AgreementNoticeInfoRes agreementNoticeInfo;
}
