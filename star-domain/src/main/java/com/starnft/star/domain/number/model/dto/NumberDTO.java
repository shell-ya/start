package com.starnft.star.domain.number.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data

public class NumberDTO implements Serializable {
    private Integer orderBy;
    private Boolean upOrDown;
    private Boolean isSell;
}
