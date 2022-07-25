package com.starnft.star.domain.compose.model.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ComposeReq implements Serializable {

    private Date startedAt;

    private Date endAt;
}
