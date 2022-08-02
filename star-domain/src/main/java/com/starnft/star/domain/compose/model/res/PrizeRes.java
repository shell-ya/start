package com.starnft.star.domain.compose.model.res;

import lombok.Data;

import java.util.HashMap;
@Data
public class PrizeRes {
    private Integer prizeType;
    private HashMap<String,Object> params;
}
