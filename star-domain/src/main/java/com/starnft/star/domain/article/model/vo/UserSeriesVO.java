package com.starnft.star.domain.article.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSeriesVO  implements Serializable {
    private String seriesName;
    private Long seriesId;
    private String seriesImages;
    private Integer nums;
}