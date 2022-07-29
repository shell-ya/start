package com.starnft.star.domain.compose.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ComposeRes implements Serializable {

    private Long id;

    private String composeName;


    private String composeImages;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endAt;


    private Integer composeStatus;


    private String composeRemark;

}
