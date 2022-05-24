package com.starnft.star.domain.support.key.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("字典")
public class DictionaryVO {
    /**
     * 字典编码
     */
    @ApiModelProperty(name = "字典编码", notes = "")
    @NotBlank(message = "dictCode 不能为空")
    private String dictCode;
    /**
     * 字典名称
     */
    @ApiModelProperty(name = "字典名称", notes = "")
    @NotBlank(message = "dictDesc 不能为空")
    private String dictDesc;
    /**
     * 分类编码
     */
    @ApiModelProperty(name = "分类编码", notes = "")
    @NotBlank(message = "categoryCode 不能为空")
    private String categoryCode;
    /**
     * 分类说明
     */
    @ApiModelProperty(name = "分类说明", notes = "")
    private String categoryDesc;
    /**
     * 排序编号
     */
    @ApiModelProperty(name = "排序编号", notes = "")
    private Integer sortNo;
    /**
     * 数据类型
     */
    @ApiModelProperty(name = "数据类型", notes = "")
    private String dataType;
    /**
     * 说明
     */
    @ApiModelProperty(name = "说明", notes = "")
    private String remark;
    /**
     * 1启用 0停止
     */
    @ApiModelProperty(name = "1启用 0停止", notes = "")
    private Integer enabled;
    /**
     * 是否加密 0不加密 1加密
     */
    @ApiModelProperty(name = "是否加密 0不加密 1加密", notes = "")
    private Integer doSecret;
}
