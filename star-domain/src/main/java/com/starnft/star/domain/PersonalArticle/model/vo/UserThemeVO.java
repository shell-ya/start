package com.starnft.star.domain.PersonalArticle.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserThemeVO  implements Serializable {
    private String seriesName;
    private Long seriesId;
    private String seriesPic;
    private Integer haveNums;
}
