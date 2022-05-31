package com.starnft.star.domain.captcha.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty("开始滑动时间")
    private Date startSlidingTime;

    @ApiModelProperty("结束滑动时间")
    private Date endSlidingTime;

    @ApiModelProperty(value = "验证码滑动轨迹")
    @NotNull(message = "验证码轨迹不能为空")
    private List<Track> trackList;

    @Data
    public static class Track {
        @ApiModelProperty(value = "event.pageX")
        private Integer x;
        @ApiModelProperty(value = "event.pageY")
        private Integer y;
        @ApiModelProperty(value = "时间")
        private Integer t;
        @ApiModelProperty(value = "类型 move/up")
        private String type;
    }
}
