package com.starnft.star.application.process.number.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2022/6/17 1:49 PM
 * @Author ： shellya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketOrderRes {
    private Integer status;
    private String message;
}
