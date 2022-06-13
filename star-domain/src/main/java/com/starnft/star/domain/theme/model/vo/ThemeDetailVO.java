package com.starnft.star.domain.theme.model.vo;

import com.starnft.star.domain.publisher.model.vo.PublisherVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThemeDetailVO implements Serializable {

    private Long id;
    private Long publisherId;
    private Long seriesId;

    private Integer themeType;

    private String themeName;

    private String themePic;

    private Integer publishNumber;

    private String descrption;

    private Byte themeLevel;

    private Integer stock;

    private BigDecimal lssuePrice;

    private String tags;
    private PublisherVO publisherVO;
    private String contractAddress;
}
