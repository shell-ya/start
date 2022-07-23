package com.starnft.star.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.starnft.star.common.annotation.Excel;
import com.starnft.star.common.core.domain.BaseEntity;

/**
 * 推广记录对象 star_nft_promote_record
 *
 * @author shellya
 * @date 2022-07-23
 */
public class StarNftPromoteRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 主键 */
    private String recipient;

    /** 内容 */
    @Excel(name = "内容")
    private String context;

    /** 发送状态 */
    @Excel(name = "发送状态")
    private Long sendStatus;

    /** 推广类型 */
    @Excel(name = "推广类型")
    private Long promoteType;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifiedAt;

    /** 订单号 */
    @Excel(name = "订单号")
    private String ordersn;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }

    public String getRecipient()
    {
        return recipient;
    }
    public void setContext(String context)
    {
        this.context = context;
    }

    public String getContext()
    {
        return context;
    }
    public void setSendStatus(Long sendStatus)
    {
        this.sendStatus = sendStatus;
    }

    public Long getSendStatus()
    {
        return sendStatus;
    }
    public void setPromoteType(Long promoteType)
    {
        this.promoteType = promoteType;
    }

    public Long getPromoteType()
    {
        return promoteType;
    }
    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }
    public void setModifiedAt(Date modifiedAt)
    {
        this.modifiedAt = modifiedAt;
    }

    public Date getModifiedAt()
    {
        return modifiedAt;
    }
    public void setOrdersn(String ordersn)
    {
        this.ordersn = ordersn;
    }

    public String getOrdersn()
    {
        return ordersn;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("recipient", getRecipient())
            .append("context", getContext())
            .append("sendStatus", getSendStatus())
            .append("promoteType", getPromoteType())
            .append("createdAt", getCreatedAt())
            .append("modifiedAt", getModifiedAt())
            .append("ordersn", getOrdersn())
            .toString();
    }
}
