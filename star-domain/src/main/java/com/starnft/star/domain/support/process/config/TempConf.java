package com.starnft.star.domain.support.process.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class TempConf implements Serializable {

    private String trade;

    private String reqTempPath;

    private String resTempPath;

    private String orderTempPath;

    private String signTempPath;

    private HttpConf httpConf;

}
