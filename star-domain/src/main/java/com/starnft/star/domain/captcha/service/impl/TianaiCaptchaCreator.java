package com.starnft.star.domain.captcha.service.impl;

import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.starnft.star.common.enums.CaptchaTypeEnum;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.captcha.model.req.StarImageCaptchaTrack;
import com.starnft.star.domain.captcha.model.vo.StarImageCaptchaVO;
import com.starnft.star.domain.captcha.service.ICaptchaCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Harlan
 * @date 2022/05/30 22:59
 */
@Component("tianaiCaptchaCreator")
public class TianaiCaptchaCreator implements ICaptchaCreator {

    @Autowired
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
        boolean matching = this.imageCaptchaApplication.matching(id, BeanColverUtil.colver(track, ImageCaptchaTrack.class));
        // 如果开启了二次验证
        if (this.imageCaptchaApplication instanceof SecondaryVerificationApplication) {
            matching = matching && ((SecondaryVerificationApplication) this.imageCaptchaApplication).secondaryVerification(id);
        }
        return matching;
    }
}
