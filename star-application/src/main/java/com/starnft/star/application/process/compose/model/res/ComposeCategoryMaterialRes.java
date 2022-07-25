package com.starnft.star.application.process.compose.model.res;

import com.starnft.star.domain.compose.model.dto.ComposeMaterialDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComposeCategoryMaterialRes implements  Serializable {
    private Long id;
    private List<ComposeMaterialDTO> composeMaterials;

    private Boolean isScore;

    private Integer composeScopeType;

    private Integer composeScopeNumber;
}
