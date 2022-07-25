package com.starnft.star.domain.compose.repository;

import com.starnft.star.domain.compose.model.res.ComposeCategoryRes;

import java.util.List;

public interface IComposeCategoryRepository {
   List<ComposeCategoryRes> queryComposeCategoryByComposeId(Long composeId);
}
