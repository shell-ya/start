package com.starnft.star.common.page;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestPage  implements Serializable {
    private int page ;
    private  int size;
}
