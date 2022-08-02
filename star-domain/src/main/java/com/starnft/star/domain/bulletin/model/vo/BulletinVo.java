package com.starnft.star.domain.bulletin.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Date 2022/7/25 8:06 PM
 * @Author ： shellya
 */
@Data
public class BulletinVo implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private Date publishTime;
    private String picUrl;
    private String content;
}
