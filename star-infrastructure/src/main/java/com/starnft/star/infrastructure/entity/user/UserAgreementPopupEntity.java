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
@TableName("user_agreement_popup")
public class UserAgreementPopupEntity extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @TableField("id")
    private Long id;

    /**
     * 弹窗编号
     */
    @TableField("agreement_popup_id")
    private String agreementPopupId;

    /**
     * 弹窗标题
     */
    @TableField("agreement_popup_title")
    private String agreementPopupTitle;

    /**
     * 弹窗场景
     */
    @TableField("agreement_popup_scene")
    private Integer agreementPopupScene;

    /**
     * 弹窗正文
     */
    @TableField("agreement_popup_content")
    private String agreementPopupContent;

    /**
     * 弹窗生效时间
     */
    @TableField("effective_time")
    private Date effectiveTime;

    private static final long serialVersionUID = 1L;
}