package com.starnft.star.domain.numbers.model.dto;

import com.starnft.star.common.page.RequestPage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/05/23 12:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class NumberQueryDTO extends RequestPage implements Serializable {
    private Integer themeType;
    private String orderBy;
    private String sortType;
    private Long seriesId;
    private Long themeId;
    private String themeName;
}
