package com.starnft.star.business.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @Date 2022/6/27 3:15 PM
 * @Author ： shellya
 */
@Data
public class PublisherVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long value;
    private String label;
    private List<PublisherVo> children;

}
