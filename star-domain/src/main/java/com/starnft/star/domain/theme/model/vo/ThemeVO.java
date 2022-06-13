package com.starnft.star.domain.theme.model.vo;

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
public class ThemeVO implements Serializable {
    private Long id;
    private Integer themeType;
    private Long seriesId;
    private String themeName;
    private String themePic;
    private Integer publishNumber;
    private Byte themeLevel;
    private BigDecimal lssuePrice;
    private Long publisherId;
    private String tags;
    private PublisherVO publisherVO;
}
