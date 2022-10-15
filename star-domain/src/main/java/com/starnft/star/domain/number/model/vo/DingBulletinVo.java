package com.starnft.star.domain.number.model.vo;

import lombok.Data;

/**
 * 盯链公告对象
 */
@Data
public class DingBulletinVo {

    /**
     * id
     */
    private Long id;

    /**
     * 正文
     */
    private String title;

    /**
     * 图片
     */
    private String imageUrl;

    /**
     * 跳转链接
     */
    private String link;
}
