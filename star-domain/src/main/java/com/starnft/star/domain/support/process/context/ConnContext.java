package com.starnft.star.domain.support.process.context;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.support.process.assign.StarRequestMethod;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class ConnContext implements Serializable {

    private String url;

    private String path;

    private StarConstants.ProcessType type;

    private StarRequestMethod restMethod;

    private HttpHeaders httpHeaders;

    private Map<String, String> formData;

    /** 请求串*/
    private String content;

}
