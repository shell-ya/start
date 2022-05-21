package com.starnft.star.application.process.wallet.req;

import com.starnft.star.common.page.RequestPage;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PayRecordReq extends RequestPage implements Serializable {

    /** userId*/
    @NotNull(message = "userId 不能为空")
    private Long userId;
    /** 开始时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /** 结束时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /** 支付状态*/
    private String payStatus;
    /** 支付类型*/
    private List<Integer> payType;
}
