package com.starnft.star.domain.user.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author WeiChunLAI
 * @date 2022/5/13 20:19
 */
@Data
public class AgreementPopupInfoVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 弹窗编号
     */
    private String agreementPopupId;

    /**
     * 弹窗标题
     */
    private String agreementPopupTitle;

    /**
     * 弹窗场景
     */
    private Byte agreementPopupScene;

    /**
     * 弹窗正文
     */
    private String agreementPopupContent;

    /**
     * 弹窗生效时间
     */
    private Date effectiveTime;

}
