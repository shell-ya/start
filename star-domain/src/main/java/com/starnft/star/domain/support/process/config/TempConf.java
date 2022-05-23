package com.starnft.star.domain.support.process.config;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TempConf implements Serializable {

    private String trade;

    private String reqTempPath;

    private String resTempPath;

    private List<HttpConf> httpConfs;

}
