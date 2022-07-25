package com.starnft.star.domain.prop.model.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PropsListRes implements Serializable {

    @ApiModelProperty(name = "道具id", notes = "")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long propId;
    /**
     * 道具数量
     */
    @ApiModelProperty(name = "道具数量", notes = "")
    private Integer propCounts;
    /**
     * 过期时间
     */
    @ApiModelProperty(name = "过期时间", notes = "")
    private Date expire;
    /**
     * 道具图标
     */
    @ApiModelProperty(name = "道具图标", notes = "")
    private String propIcon;
    /**
     * 道具等级
     */
    @ApiModelProperty(name = "道具等级", notes = "")
    private Integer propLevel;
    /**
     * 道具类型
     */
    @ApiModelProperty(name = "道具类型", notes = "")
    private Integer propType;
}
