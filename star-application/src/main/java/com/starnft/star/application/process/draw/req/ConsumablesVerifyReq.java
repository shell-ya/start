package com.starnft.star.application.process.draw.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumablesVerifyReq {

    private Integer consumableType;

    private Long uid;

    private String uniqueItemId;
}
