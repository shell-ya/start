package com.starnft.star.business.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSeriesVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  seriesId;
    private String seriesName;
    @JsonSerialize(using = ToStringSerializer.class)
    private String  userId;
    private String seriesImages;
    private Integer types;
    private Integer nums;
    private Integer status;

}
