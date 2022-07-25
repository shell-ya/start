package com.starnft.star.application.process.compose;

import com.starnft.star.application.process.compose.model.res.ComposeCategoryMaterialRes;

import java.util.List;

public interface IComposeCore {
    List<ComposeCategoryMaterialRes> composeMaterial(Long id);
}
