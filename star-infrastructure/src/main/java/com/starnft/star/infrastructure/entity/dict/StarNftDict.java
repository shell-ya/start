package com.starnft.star.infrastructure.entity.dict;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "字典表",description = "")
@TableName("star_nft_dict")
public class StarNftDict extends BaseEntity implements Serializable {

     /** id */
    @ApiModelProperty(name = "id",notes = "")
    @TableId
    private Long id ;
    /** 字典编码 */
    @ApiModelProperty(name = "字典编码",notes = "")
    @NotBlank(message = "dictCode 不能为空")
    private String dictCode ;
    /** 字典名称 */
    @ApiModelProperty(name = "字典名称",notes = "")
    @NotBlank(message = "dictDesc 不能为空")
    private String dictDesc ;
    /** 分类编码 */
    @ApiModelProperty(name = "分类编码",notes = "")
    @NotBlank(message = "categoryCode 不能为空")
    private String categoryCode ;
    /** 分类说明 */
    @ApiModelProperty(name = "分类说明",notes = "")
    private String categoryDesc ;
    /** 排序编号 */
    @ApiModelProperty(name = "排序编号",notes = "")
    private Integer sortNo ;
    /** 数据类型 */
    @ApiModelProperty(name = "数据类型",notes = "")
    private String dataType ;
    /** 说明 */
    @ApiModelProperty(name = "说明",notes = "")
    private String remark ;
    /** 检索标识 */
    @ApiModelProperty(name = "检索标识",notes = "")
    private String locateCode ;
    /** 版本号 */
    @ApiModelProperty(name = "版本号",notes = "")
    private Integer version ;

    @ApiModelProperty(name = "1启用 0停止",notes = "")
    private Integer enabled;

    @ApiModelProperty(name = "是否加密 0不加密 1加密",notes = "")
    private Integer doSecret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocateCode() {
        return locateCode;
    }

    public void setLocateCode(String locateCode) {
        this.locateCode = locateCode;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getDoSecret() {
        return doSecret;
    }

    public void setDoSecret(Integer doSecret) {
        this.doSecret = doSecret;
    }
}
