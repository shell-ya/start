package com.starnft.star.domain.compose.model.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ComposeRes implements Serializable {

    private Long id;

    private String composeName;


    private String composeImages;


    private Date startedAt;


    private Date endAt;


    private Integer composeStatus;


    private String composeRemark;

}
