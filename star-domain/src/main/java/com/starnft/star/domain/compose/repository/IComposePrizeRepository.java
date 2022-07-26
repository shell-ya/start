package com.starnft.star.domain.compose.repository;
import com.starnft.star.domain.compose.model.res.ComposePrizeRes;

import java.util.List;

public interface IComposePrizeRepository {
    List<ComposePrizeRes> queryComposePrizeByComposeId(Long composeId);
}
