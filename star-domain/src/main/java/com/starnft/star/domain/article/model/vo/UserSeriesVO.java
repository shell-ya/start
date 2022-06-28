package com.starnft.star.domain.article.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    private String seriesName;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesId;
    private String seriesImages;
    private Integer nums;
    private String publisherName;
    private String publisherPic;
}
