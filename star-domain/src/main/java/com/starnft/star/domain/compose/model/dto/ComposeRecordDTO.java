package com.starnft.star.domain.compose.model.dto;

import com.starnft.star.domain.compose.model.res.PrizeRes;
import lombok.Data;

import java.util.List;

@Data
public class ComposeRecordDTO {

    private Long id;


    private Long userId;

    private Long composeId;


    private Long categoryId;

    private List<ComposeUserArticleNumberDTO> source;


    private Boolean isScope;

    private Integer scopeNumber;

    private Integer prizeType;

    private PrizeRes prizeMessage;
}
