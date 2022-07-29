package com.starnft.star.domain.compose.model.req;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class ComposeReq implements Serializable {
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private Date startedAt;
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private Date endAt;
}
