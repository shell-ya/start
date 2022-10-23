package com.starnft.star.business.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;


/**
 * 快照对象 star_nft_snapshot
 */
@TableName("star_nft_snapshot")
public class StarNftSnapshot extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 任务名 */
    @Excel(name = "任务名")
    private String taskName;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String snapshotMaterial;

    /** 执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "执行时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date executionTime;

    /** 状态 0:初始化 1: 成功 2:失败 */
    @Excel(name = "状态 0:初始化 1: 成功 2:失败")
    private Integer successful;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date createAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date updateAt;

    /** 定时任务ID */
    private Long taskId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskName()
    {
        return taskName;
    }
    public void setSnapshotMaterial(String snapshotMaterial)
    {
        this.snapshotMaterial = snapshotMaterial;
    }

    public String getSnapshotMaterial()
    {
        return snapshotMaterial;
    }
    public void setExecutionTime(Date executionTime)
    {
        this.executionTime = executionTime;
    }

    public Date getExecutionTime()
    {
        return executionTime;
    }
    public void setSuccessful(Integer successful)
    {
        this.successful = successful;
    }

    public Integer getSuccessful()
    {
        return successful;
    }
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }
    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }
    public void setCreateAt(Date createAt)
    {
        this.createAt = createAt;
    }

    public Date getCreateAt()
    {
        return createAt;
    }
    public void setUpdateAt(Date updateAt)
    {
        this.updateAt = updateAt;
    }

    public Date getUpdateAt()
    {
        return updateAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskName", getTaskName())
            .append("snapshotMaterial", getSnapshotMaterial())
            .append("executionTime", getExecutionTime())
            .append("successful", getSuccessful())
            .append("filePath", getFilePath())
            .append("isDeleted", getIsDeleted())
            .append("createAt", getCreateAt())
            .append("updateAt", getUpdateAt())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
