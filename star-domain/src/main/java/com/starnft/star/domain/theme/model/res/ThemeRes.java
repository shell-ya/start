package com.starnft.star.domain.theme.model.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ThemeRes implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Integer themeType;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long seriesId;
    private String themeName;
    private String themePic;
    private Integer publishNumber;
    private Byte themeLevel;
    private BigDecimal lssuePrice;
//    private Long publisherId;
    private String tags;
  private PublisherVO publisherVO;
}

