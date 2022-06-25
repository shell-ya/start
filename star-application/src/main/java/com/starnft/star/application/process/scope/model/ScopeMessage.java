package com.starnft.star.application.process.scope.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ScopeMessage implements Serializable {
    private Integer event;
    private Long userId;
    private Map<String,Object> extend;
}
