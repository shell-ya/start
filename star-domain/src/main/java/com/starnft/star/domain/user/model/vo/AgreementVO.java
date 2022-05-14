package com.starnft.star.domain.user.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author WeiChunLAI
 * @date 2022/5/13 11:47
 */
@Data
public class AgreementVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 协议自定义id
     */
    private String agreementId;

    /**
     * 协议名称
     */
    private String agreementName;

    /**
     * 协议场景 1=注册
     */
    private Integer agreementScene;

    /**
     * 协议类型 1=隐私 2=服务
     */
    private Integer agreementType;

    /**
     * 协议版本
     */
    private String agreementVersion;

    /**
     * 协议生效日期
     */
    private Date effectiveTime;

    /**
     * 提交状态 1=未提交 2=已提交
     */
    private Integer submitStatus;

    /**
     * 协议内容
     */
    private String agreementContent;
}
