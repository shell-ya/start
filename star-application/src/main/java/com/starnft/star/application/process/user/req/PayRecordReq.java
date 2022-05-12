package com.starnft.star.application.process.user.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starnft.star.common.page.RequestPage;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PayRecordReq extends RequestPage implements Serializable {

    /** userId*/
    @NotBlank(message = "userId 不能为空")
    private Long userId;
    /** 开始时间*/
    @JsonFormat(locale = "GMT-8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /** 结束时间*/
    @JsonFormat(locale = "GMT-8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /** 支付状态*/
    private String payStatus;
    /** 支付类型*/
    private List<Integer> payType;
}
