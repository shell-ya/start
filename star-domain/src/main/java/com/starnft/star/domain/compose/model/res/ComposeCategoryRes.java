package com.starnft.star.domain.compose.model.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
@Data
public class ComposeCategoryRes implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String composeMaterial;

    private Boolean isScore;

    private Integer composeScopeType;

    private Integer composeScopeNumber;
}
