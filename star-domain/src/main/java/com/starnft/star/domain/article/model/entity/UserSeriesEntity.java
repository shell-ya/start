package com.starnft.star.domain.article.model.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserSeriesEntity implements Serializable {
    private  String seriesName;
    private String seriesImages;
    private Integer nums;

}
