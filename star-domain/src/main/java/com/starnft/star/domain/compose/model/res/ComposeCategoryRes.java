package com.starnft.star.domain.compose.model.res;

import lombok.Data;

import java.io.Serializable;
@Data
public class ComposeCategoryRes implements Serializable {

    private Long id;
    private String composeMaterial;

    private Boolean isScore;

    private Integer composeScopeType;

    private Integer composeScopeNumber;
}
