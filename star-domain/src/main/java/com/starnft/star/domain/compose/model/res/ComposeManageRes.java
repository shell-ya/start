package com.starnft.star.domain.compose.model.res;

import lombok.Data;

import java.io.Serializable;
@Data
public class ComposeManageRes implements Serializable {
    private  Boolean isSuccess;
    private PrizeRes  prizeRes;

}
