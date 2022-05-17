package com.starnft.star.application.process.user.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AgreementNoticeInfoRes {

    /**
     * 弹窗标题
     */
    @ApiModelProperty("弹窗标题")
    private String noticeTitle;

    /**
     * 弹窗正文
     */
    @ApiModelProperty("弹窗正文")
    private String noticeContent;
}
