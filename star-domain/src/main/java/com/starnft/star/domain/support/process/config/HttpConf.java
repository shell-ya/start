package com.starnft.star.domain.support.process.config;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class HttpConf implements Serializable {

    private String processType;

    private String apiUrl;

    private Map<String, String> properties;

}
