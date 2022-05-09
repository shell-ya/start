package com.starnft.star.domain.series.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SeriesVO implements Serializable {
    private Long id;
    private String seriesName;
    private Boolean seriesType;
    private String seriesImages;
    private String seriesModels;
    private Integer seriesStatus;
}
