package com.starnft.star.domain.support.process.context;

import com.starnft.star.common.constant.StarConstants;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

@Data
@Builder
public class ConnContext implements Serializable {

    private String url;

    private String path;

    private StarConstants.ProcessType type;

    private RequestMethod restMethod;

    private HttpHeaders httpHeaders;

    /** 请求串*/
    private String content;

}
