package com.starnft.star.business.domain.dto;

/**
 * @Date 2022/7/30 2:11 PM
 * @Author ： shellya
 */

import lombok.Data;

import java.util.List;

@Data
public class RecordItem {
    private Long seriesId;
    private Long seriesThemeInfoId;
    private List<Long> seriesThemeId;
}
