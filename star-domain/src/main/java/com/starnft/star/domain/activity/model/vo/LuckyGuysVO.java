package com.starnft.star.domain.activity.model.vo;

import com.starnft.star.common.annotations.Desensitized;
import com.starnft.star.common.enums.SensitiveTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LuckyGuysVO implements Serializable {

    private String awardId;

    private String awardName;

    @Desensitized(type = SensitiveTypeEnum.ACCOUNT)
    private String luckyUid;

    private Date luckyTime;
}
