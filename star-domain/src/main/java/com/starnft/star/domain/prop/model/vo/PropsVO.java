package com.starnft.star.domain.prop.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropsVO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    private Long id;
    /**
     * 道具类型
     */
    @ApiModelProperty(name = "道具类型", notes = "")
    private Integer propType;
    /**
     * 道具名称
     */
    @ApiModelProperty(name = "道具名称", notes = "")
    private String propName;
    /**
     * 道具等级
     */
    @ApiModelProperty(name = "道具等级", notes = "")
    private Integer propLevel;
    /**
     * 道具过期时间
     */
    @ApiModelProperty(name = "道具过期时间", notes = "")
    private Date expire;
    /**
     * 道具图标
     */
    @ApiModelProperty(name = "道具图标", notes = "")
    private String propIcon;
    /**
     * 道具可使用时间
     */
    @ApiModelProperty(name = "道具可使用时间", notes = "")
    private Date propTime;
    /**
     * 道具描述
     */
    @ApiModelProperty(name = "道具描述", notes = "")
    private String propDesc;
}
