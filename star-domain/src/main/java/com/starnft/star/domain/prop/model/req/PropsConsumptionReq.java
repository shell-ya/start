package com.starnft.star.domain.prop.model.req;

import lombok.Data;

import java.io.Serializable;
import java.util.function.Consumer;

@Data
public class PropsConsumptionReq implements Serializable {

    private Long userId;

    private Long propId;

    private Consumer invoker;
}
