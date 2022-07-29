package com.starnft.star.application.process.compose.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ComposeDetailRes implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String composeName;


    private String composeImages;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endAt;


    private Integer composeStatus;


    private String composeRemark;
    private List<ComposeCategoryMaterialRes> composeCategoryMaterialResList;
}
