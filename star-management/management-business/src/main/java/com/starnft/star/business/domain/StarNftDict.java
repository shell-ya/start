package com.starnft.star.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 字典对象 star_nft_dict
 *
 * @author shellya
 * @date 2022-06-08
 */
public class StarNftDict extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** isDeleted */
    @Excel(name = "isDeleted")
    private Long isDeleted;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date createdAt;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 修改日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "修改日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date modifiedAt;

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifiedBy;

    /** 字典编码 */
    @Excel(name = "字典编码")
    private String dictCode;

    /** 字典名称 */
    @Excel(name = "字典名称")
    private String dictDesc;

    /** 分类编码 */
    @Excel(name = "分类编码")
    private String categoryCode;

    /** 分类说明 */
    @Excel(name = "分类说明")
    private String categoryDesc;

    /** 排序编号 */
    @Excel(name = "排序编号")
    private Long sortNo;

    /** 数据类型 */
    @Excel(name = "数据类型")
    private String dataType;

    /** 检索标识 */
    @Excel(name = "检索标识")
    private String locateCode;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long version;

    /** 开启或则关闭 */
    @Excel(name = "开启或则关闭")
    private Integer enabled;

    /** 是否加密 */
    @Excel(name = "是否加密")
    private Integer doSecret;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setIsDeleted(Long isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted()
    {
        return isDeleted;
    }
    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setModifiedAt(Date modifiedAt)
    {
        this.modifiedAt = modifiedAt;
    }

    public Date getModifiedAt()
    {
        return modifiedAt;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setDictCode(String dictCode)
    {
        this.dictCode = dictCode;
    }

    public String getDictCode()
    {
        return dictCode;
    }
    public void setDictDesc(String dictDesc)
    {
        this.dictDesc = dictDesc;
    }

    public String getDictDesc()
    {
        return dictDesc;
    }
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode()
    {
        return categoryCode;
    }
    public void setCategoryDesc(String categoryDesc)
    {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryDesc()
    {
        return categoryDesc;
    }
    public void setSortNo(Long sortNo)
    {
        this.sortNo = sortNo;
    }

    public Long getSortNo()
    {
        return sortNo;
    }
    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getDataType()
    {
        return dataType;
    }
    public void setLocateCode(String locateCode)
    {
        this.locateCode = locateCode;
    }

    public String getLocateCode()
    {
        return locateCode;
    }
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }

    public Integer getEnabled()
    {
        return enabled;
    }
    public void setDoSecret(Integer doSecret)
    {
        this.doSecret = doSecret;
    }

    public Integer getDoSecret()
    {
        return doSecret;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("isDeleted", getIsDeleted())
            .append("createdAt", getCreatedAt())
            .append("createdBy", getCreatedBy())
            .append("modifiedAt", getModifiedAt())
            .append("modifiedBy", getModifiedBy())
            .append("dictCode", getDictCode())
            .append("dictDesc", getDictDesc())
            .append("categoryCode", getCategoryCode())
            .append("categoryDesc", getCategoryDesc())
            .append("sortNo", getSortNo())
            .append("dataType", getDataType())
            .append("remark", getRemark())
            .append("locateCode", getLocateCode())
            .append("version", getVersion())
            .append("enabled", getEnabled())
            .append("doSecret", getDoSecret())
            .toString();
    }
}
