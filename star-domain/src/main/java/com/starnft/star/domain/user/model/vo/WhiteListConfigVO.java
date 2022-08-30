package com.starnft.star.domain.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WhiteListConfigVO {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    private Long id;
    /**
     * 白名单描述
     */
    @ApiModelProperty(name = "白名单描述", notes = "")
    private String whiteDesc;
    /**
     * 白名单类型
     */
    @ApiModelProperty(name = "白名单类型", notes = "")
    private Integer whiteType;
    /**
     * 是否生效
     */
    @ApiModelProperty(name = "是否生效", notes = "")
    private Integer effective;

    /**
     * 商品id
     */
    @ApiModelProperty(name = "商品id", notes = "")
    private Long goodsId;

    /**
     * 有效开始时间
     */
    @ApiModelProperty(name = "有效开始时间", notes = "")
    private Date startTime;

    /**
     * 有效结束时间
     */
    @ApiModelProperty(name = "有效结束时间", notes = "")
    private Date endTime;

}
