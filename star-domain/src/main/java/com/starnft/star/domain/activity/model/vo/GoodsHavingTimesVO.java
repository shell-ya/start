package com.starnft.star.domain.activity.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsHavingTimesVO implements Serializable {

    /**
     * 藏品主题id
     */
    @ApiModelProperty(name = "藏品主题id", notes = "")
    private Long themeInfoId;
    /**
     * 持有人id
     */
    @ApiModelProperty(name = "持有人id", notes = "")
    private Long uid;
    /**
     * 持有累积天数
     */
    @ApiModelProperty(name = "持有累积天数", notes = "")
    private Integer countTimes;
    /**
     * 乐观锁
     */
    @ApiModelProperty(name = "乐观锁", notes = "")
    private Integer version;
}
