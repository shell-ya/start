package com.starnft.star.domain.banner.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Date 2022/5/9 9:16 PM
 * @Author ： shellya
 */
@Data
public class BannerDto {


    private Long id;
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 图片地址
     */
    @NotBlank(message =  "图片地址不能为空")
    private String imgUrl;

    /**
     * 展示位置：头部，TOP; 中间，MIDDLE；底部，BOTTOM;
     */
    @NotBlank(message =  "展示位置")
    private String position;

    /**
     * 是否展示：0，否；1，是
     */
    private Byte display;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 爬虫地址
     */
    private String crawlersUrl;

    /**
     * 外链地址
     */
    @NotBlank(message =  "外链地址")
    private String url;

}
