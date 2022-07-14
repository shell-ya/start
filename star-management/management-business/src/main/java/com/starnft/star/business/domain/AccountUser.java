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
 * 用户对象 account_user
 *
 * @author shellya
 * @date 2022-05-28
 */
public class AccountUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 账号 */
    @Excel(name = "账号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long account;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 用户手机号 */
    @Excel(name = "用户手机号")
    private String phone;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickName;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String avatar;

    /** 支付密码 */
    @Excel(name = "支付密码")
    private String plyPassword;

    /** 是否禁用 */
    @Excel(name = "是否禁用")
    private Integer isActive;

    /** 真实名称 */
    @Excel(name = "真实名称")
    private String fullName;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idNumber;

    /** 是否实名认证 */
    @Excel(name = "是否实名认证")
    private Long realPersonFlag;

    /** 是否删除 */
    @Excel(name = "是否删除(0-未删除 1-已删除)")
    private Integer isDeleted;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date createdAt;

    /** 创建用户 */
    @Excel(name = "创建用户")
    private String createdBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:SS")
    private Date modifiedAt;

    /** 更新用户 */
    @Excel(name = "更新用户")
    private String modifiedBy;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setAccount(Long account)
    {
        this.account = account;
    }

    public Long getAccount()
    {
        return account;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getNickName()
    {
        return nickName;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
    }
    public void setPlyPassword(String plyPassword)
    {
        this.plyPassword = plyPassword;
    }

    public String getPlyPassword()
    {
        return plyPassword;
    }
    public void setIsActive(Integer isActive)
    {
        this.isActive = isActive;
    }

    public Integer getIsActive()
    {
        return isActive;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getFullName()
    {
        return fullName;
    }
    public void setIdNumber(String idNumber)
    {
        this.idNumber = idNumber;
    }

    public String getIdNumber()
    {
        return idNumber;
    }
    public void setRealPersonFlag(Long realPersonFlag)
    {
        this.realPersonFlag = realPersonFlag;
    }

    public Long getRealPersonFlag()
    {
        return realPersonFlag;
    }
    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted()
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("account", getAccount())
            .append("password", getPassword())
            .append("phone", getPhone())
            .append("nickName", getNickName())
            .append("avatar", getAvatar())
            .append("plyPassword", getPlyPassword())
            .append("isActive", getIsActive())
            .append("fullName", getFullName())
            .append("idNumber", getIdNumber())
            .append("realPersonFlag", getRealPersonFlag())
            .append("isDeleted", getIsDeleted())
            .append("createdAt", getCreatedAt())
            .append("createdBy", getCreatedBy())
            .append("modifiedAt", getModifiedAt())
            .append("modifiedBy", getModifiedBy())
            .toString();
    }
}
