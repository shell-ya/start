package com.starnft.star.business.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class SnapshotMaterialDto implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    //主题id
    private Long  themeId;
    //主题名称
    private String themeName;
}
