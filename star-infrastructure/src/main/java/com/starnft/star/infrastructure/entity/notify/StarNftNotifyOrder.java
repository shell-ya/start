package com.starnft.star.infrastructure.entity.notify;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="com-starnft-star-infrastructure-entity-notify-StarNftNotifyOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_notify_order")
public class StarNftNotifyOrder implements Serializable {
    @TableField(value = "id")
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "`uid`")
    @ApiModelProperty(value="")
    private Long uid;

    @TableField(value = "order_sn")
    @ApiModelProperty(value="")
    private String orderSn;

    @TableField(value = "trans_sn")
    @ApiModelProperty(value="")
    private String transSn;

    @TableField(value = "total_amount")
    @ApiModelProperty(value="")
    private BigDecimal totalAmount;

    @TableField(value = "pay_channel")
    @ApiModelProperty(value="")
    private String payChannel;

    @TableField(value = "message")
    @ApiModelProperty(value="")
    private String message;

    @TableField(value = "`status`")
    @ApiModelProperty(value="")
    private Integer status;

    @TableField(value = "create_time")
    @ApiModelProperty(value="")
    private Date createTime;

    @TableField(value = "pay_time")
    @ApiModelProperty(value="")
    private Date payTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_UID = "uid";

    public static final String COL_ORDER_SN = "order_sn";

    public static final String COL_TRANS_SN = "trans_sn";

    public static final String COL_TOTAL_AMOUNT = "total_amount";

    public static final String COL_PAY_CHANNEL = "pay_channel";

    public static final String COL_MESSAGE = "message";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_PAY_TIME = "pay_time";
}