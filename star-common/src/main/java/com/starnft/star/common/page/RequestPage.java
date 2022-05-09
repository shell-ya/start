package com.starnft.star.common.page;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPage implements Serializable {
    private int page ;
    private  int size;
}
