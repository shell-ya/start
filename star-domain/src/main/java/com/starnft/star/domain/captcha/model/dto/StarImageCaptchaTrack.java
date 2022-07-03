package com.starnft.star.domain.captcha.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Harlan
 * @date 2022/05/30 23:22
 */
@Data
public class StarImageCaptchaTrack implements Serializable {
    @ApiModelProperty("背景图片宽")
    private Integer bgImageWidth;

    @ApiModelProperty("背景图片高")
    private Integer bgImageHeight;

    @ApiModelProperty("滑块图片宽")
    private Integer sliderImageWidth;

    @ApiModelProperty("滑块图片高")
    private Integer sliderImageHeight;

    @ApiModelProperty("开始滑动时间 格式：yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startSlidingTime;

    @ApiModelProperty("结束滑动时间 格式：yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endSlidingTime;

    @ApiModelProperty(value = "验证码滑动轨迹")
    @NotNull(message = "验证码轨迹不能为空")
    private List<StarTrack> trackList;

}
