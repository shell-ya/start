package com.starnft.star.domain.captcha.service.impl;

import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.starnft.star.common.enums.CaptchaTypeEnum;
import com.starnft.star.domain.captcha.model.dto.StarImageCaptchaTrack;
import com.starnft.star.domain.captcha.model.vo.StarImageCaptchaVO;
import com.starnft.star.domain.captcha.service.ICaptchaCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @author Harlan
 * @date 2022/05/30 22:59
 */
@Component("tianaiCaptchaCreator")
public class TianaiCaptchaCreator implements ICaptchaCreator {

    @Autowired(required = false)
    private ImageCaptchaApplication imageCaptchaApplication;

    @Override
    public StarImageCaptchaVO generate(CaptchaTypeEnum typeEnum) {
        CaptchaResponse<ImageCaptchaVO> response = this.imageCaptchaApplication.generateCaptcha(typeEnum.getType());
        ImageCaptchaVO captcha = response.getCaptcha();
        return StarImageCaptchaVO.builder()
                .id(response.getId())
                .bgImage(captcha.getBackgroundImage())
                .bgImageWidth(captcha.getBackgroundImageWidth())
                .bgImageHeight(captcha.getBackgroundImageHeight())
                .sliderImage(captcha.getSliderImage())
                .sliderImageHeight(captcha.getSliderImageHeight())
                .sliderImageWidth(captcha.getSliderImageWidth())
                .build();
    }

    @Override
    public Boolean check(String id, StarImageCaptchaTrack track) {
        boolean matching = this.imageCaptchaApplication.matching(id, this.convert(track));
        // 如果开启了二次验证
        if (this.imageCaptchaApplication instanceof SecondaryVerificationApplication) {
            matching = matching && ((SecondaryVerificationApplication) this.imageCaptchaApplication).secondaryVerification(id);
        }
        return matching;
    }

    private ImageCaptchaTrack convert(StarImageCaptchaTrack track) {
        ImageCaptchaTrack imageCaptchaTrack = new ImageCaptchaTrack();
        imageCaptchaTrack.setSliderImageHeight(track.getSliderImageHeight());
        imageCaptchaTrack.setSliderImageWidth(track.getSliderImageWidth());
        imageCaptchaTrack.setBgImageWidth(track.getBgImageWidth());
        imageCaptchaTrack.setBgImageHeight(track.getBgImageHeight());
        imageCaptchaTrack.setStartSlidingTime(track.getStartSlidingTime());
        imageCaptchaTrack.setEndSlidingTime(track.getEndSlidingTime());
        imageCaptchaTrack.setTrackList(track.getTrackList().stream().map(trackItem -> {
            ImageCaptchaTrack.Track imgTrack = new ImageCaptchaTrack.Track();
            imgTrack.setT(trackItem.getT());
            imgTrack.setX(trackItem.getX());
            imgTrack.setY(trackItem.getY());
            imgTrack.setType(trackItem.getType());
            return imgTrack;
        }).collect(Collectors.toList()));
        return imageCaptchaTrack;
    }
}
