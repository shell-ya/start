package com.starnft.star.application.process.compose.model.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ComposeDetailRes implements Serializable {
    private Long id;

    private String composeName;


    private String composeImages;


    private Date startedAt;


    private Date endAt;


    private Integer composeStatus;


    private String composeRemark;
    private List<ComposeCategoryMaterialRes> composeCategoryMaterialResList;
}
