package com.starnft.star.domain.order.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("订单数据响应")
public class OrderListRes implements Serializable {

    @ApiModelProperty(value = "订单号")
    private String orderSn;
    @ApiModelProperty(value = "主题编号")
    private Integer themeNumber;
    @ApiModelProperty(value = "主题名称")
    private String themeName;
    @ApiModelProperty(value = "主题图片")
    private String themePic;
    @ApiModelProperty(value = "主题类型：1-藏品 2-盲盒")
    private Integer themeType;
    @ApiModelProperty(value = "应付金额")
    private String payAmount;
    @ApiModelProperty(value = "状态(0-待支付 1-已完成 2-已取消)")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createdAt;
    @ApiModelProperty(value = "备注")
    private String remark;

}
