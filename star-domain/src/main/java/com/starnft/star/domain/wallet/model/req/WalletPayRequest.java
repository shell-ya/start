package com.starnft.star.domain.wallet.model.req;

import com.starnft.star.common.Result;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Supplier;

@Data
@ApiModel("交易请求")
public class WalletPayRequest implements Serializable {

    @ApiModelProperty(value = "uid")
    private Long userId;

    @ApiModelProperty(value = "from uid")
    private Long fromUid;

    @ApiModelProperty(value = "to uid")
    private Long toUid;

    @ApiModelProperty(value = "交易流水号")
    private String orderSn;

    @ApiModelProperty(value = "第三方交易流水号")
    private String outTradeNo;

    @ApiModelProperty(value = "实付金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "总计金额")
    private BigDecimal totalPayAmount;

    @ApiModelProperty(value = "交易手续费")
    private BigDecimal fee;

    @ApiModelProperty(value = "交易类型")
    private Integer type;

    @ApiModelProperty(value = "交易状态")
    private String status;

    @ApiModelProperty(value = "交易渠道")
    private String channel;

    @ApiModelProperty(value = "交易记录修改操作 为null 则创建交易记录")
    private Supplier<Result> doModifyRecord;


}
