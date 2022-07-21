package com.starnft.star.business.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSeriesVO implements Serializable {
    private Long  seriesId;
    private String seriesName;
    private Integer types;
    private Integer nums;

}
