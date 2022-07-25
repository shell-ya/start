package com.starnft.star.domain.prop.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropsRelationVO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty(name = "用户id", notes = "")
    private Long uid;
    /**
     * 道具id
     */
    @ApiModelProperty(name = "道具id", notes = "")
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
