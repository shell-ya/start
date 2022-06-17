package com.starnft.star.domain.number.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Date 2022/6/17 3:30 PM
 * @Author ： shellya
 */
@Data
public class ThemeNumberVo implements Serializable {

    /** 主题编号id */
    private Long numberId;
    /** 藏品价格 */
    private BigDecimal price;
    /** 主题编号 */
    private Integer themeNumber;
    /** 主题信息id*/
    private Long themeInfoId;
    /** 主题名称 */
    private String themeName;
    /** 主题图片 */
    private String themePic ;
    /** 主题类型：1-藏品;2-盲盒 */
    private Integer themeType ;
    /** 系列id */
    private Long seriesId;
    /** 系列名称 */
    private String seriesName;

}
