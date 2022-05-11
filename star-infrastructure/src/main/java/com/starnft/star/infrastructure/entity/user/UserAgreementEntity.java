package com.starnft.star.infrastructure.entity.user;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author 
 * 
 */
@Data
@TableName("user_agreement")
public class UserAgreementEntity extends BaseEntity implements Serializable{
    /**
     * 主键id
     */
    @Id
    @TableField("id")
    private Long id;

    /**
     * 协议自定义id
     */
    @TableField("agreement_id")
    private String agreementId;

    /**
     * 协议名称
     */
    @TableField("agreement_name")
    private String agreementName;

    /**
     * 协议场景 1=注册
     */
    @TableField("agreement_scene")
    private Integer agreementScene;

    /**
     * 协议类型 1=隐私 2=服务
     */
    @TableField("agreement_type")
    private Integer agreementType;

    /**
     * 协议版本
     */
    @TableField("agreement_version")
    private String agreementVersion;

    /**
     * 协议生效日期
     */
    @TableField("effective_time")
    private Date effectiveTime;

    /**
     * 提交状态 1=未提交 2=已提交
     */
    @TableField("submit_status")
    private Integer submitStatus;

    /**
     * 协议内容
     */
    @TableField("agreement_content")
    private String agreementContent;

    private static final long serialVersionUID = 1L;
}