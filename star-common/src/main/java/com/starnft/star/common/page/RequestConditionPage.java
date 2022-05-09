package com.starnft.star.common.page;

import lombok.Data;

@Data
public class RequestConditionPage<T> {
    private int page ;
    private  int size;
    private T condition;
}
