package com.starnft.star.domain.article.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Date 2022/7/24 2:09 PM
 * @Author ： shellya
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class UserThemeDetailVo implements Serializable {
//    @ApiModelProperty("商品id")
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long id;
//    @ApiModelProperty("主题id")
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long themeId;
//    @ApiModelProperty("商品编号")
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long number;
//    @ApiModelProperty("发行数量")
//    private Integer issuedQty;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品图片")
    private String picture;
    @ApiModelProperty("合约地址")
    private String contractAddress;
    @ApiModelProperty("链上标识")
    private String chainIdentification;
    @ApiModelProperty("商品描述")
    private String descrption;
    @ApiModelProperty("商品类型(1-藏品 2-盲盒")
    private Integer type;
    @ApiModelProperty("可转售 0暂不可转售 1 可转售")
    private Integer resale;
}
