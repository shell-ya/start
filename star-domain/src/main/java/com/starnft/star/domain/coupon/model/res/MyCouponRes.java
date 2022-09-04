package com.starnft.star.domain.coupon.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ：Lai Wei Chun
 * @date ：Created in 2022/9/4 1:25 上午
 * @description： TODO
 */
@Data
@Accessors(chain = true)
public class MyCouponRes {

    @ApiModelProperty("卡劵标题")
    private String composeName;

    @ApiModelProperty("卡劵封面图")
    private String images;

    @ApiModelProperty("有效期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endAt;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("优惠卷类型")
    private Integer type;

    @ApiModelProperty("优惠卷id")
    private String couponId;
}
