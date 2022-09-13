package com.starnft.star.business.domain.vo;

import lombok.Data;

@Data
public class GiveReq {
    private Long fromUid;
    private Long toUid;
    private Long seriesThemeId;
}
