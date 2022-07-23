package com.starnft.star.domain.prop.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date propTime;
    /**
     * 道具描述
     */
    @ApiModelProperty(name = "道具描述", notes = "")
    private String propDesc;

    /**
     * 道具执行器
     */
    @ApiModelProperty(name = "道具执行器", notes = "")
    @JsonIgnore
    private String execution;

    /**
     * 是否上架
     */
    @ApiModelProperty(name = "是否上架 0未上架 1上架", notes = "")
    private Integer onSell;

    /**
     * 出售价格
     */
    @ApiModelProperty(name = "出售价格", notes = "")
    private BigDecimal price;

    /**
     * 是否可以购买
     */
    @ApiModelProperty(name = "是否可以购买", notes = "")
    private Integer canBuy;

    /**
     * 出售数量 -1 不限量
     */
    @ApiModelProperty(name = "出售数量 -1 不限量", notes = "")
    private BigDecimal stock;
}
