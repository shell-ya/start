package com.starnft.star.application.process.wallet.req;

import com.starnft.star.common.page.RequestPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("交易记录请求")
public class PayRecordReq extends RequestPage implements Serializable {

    /**
     * userId
     */
    @ApiModelProperty(value = "uid", required = false)
    private Long userId;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态  待支付 WAIT_PAY 支付中 PAY_ING 支付成功 PAY_SUCCESS 支付失败 PAY_FAILED 支付关闭 PAY_CLOSE")
    private String payStatus;
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "交易类型 1充值 2提现 3退款 4买入 5卖出")
    private List<Integer> payType;
}
