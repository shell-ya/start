package com.starnft.star.domain.bulletin.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Date 2022/7/25 8:06 PM
 * @Author ： shellya
 */
@Data
public class BulletinVo {

    private Long id;
    private String title;
    private Date publishTime;
    private String picUrl;
    private String content;
}
