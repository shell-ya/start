package com.starnft.star.domain.user.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WeiChunLAI
 * @date 2022/5/13 10:54
 */
@Data
@Accessors(chain = true)
public class PopupAgreementInfo {

    private String agreementId;

    private String agreementName;
}