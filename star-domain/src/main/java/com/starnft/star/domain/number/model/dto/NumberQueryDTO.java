package com.starnft.star.domain.number.model.dto;

import com.starnft.star.common.page.RequestPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author Harlan
 * @date 2022/05/23 12:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NumberQueryDTO extends RequestPage implements Serializable {
    private Integer themeType;
    private String orderBy;
    private String sortType;
    private Long seriesId;
    private Long themeId;
    private String themeName;
}
