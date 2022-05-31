package com.starnft.star.domain.captcha.service.impl;

import com.starnft.star.common.enums.CaptchaTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.captcha.model.req.ImageCaptchaCheckReq;
import com.starnft.star.domain.captcha.model.req.ImageCaptchaGenReq;
import com.starnft.star.domain.captcha.model.vo.StarImageCaptchaVO;
import com.starnft.star.domain.captcha.service.ICaptchaService;
import com.starnft.star.domain.captcha.service.factory.CaptchaCodeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Harlan
 * @date 2022/05/30 20:20
 */
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements ICaptchaService {

    private final CaptchaCodeFactory captchaCodeFactory;

    @Override
    public StarImageCaptchaVO generateCaptcha(ImageCaptchaGenReq req) {
        return this.captchaCodeFactory.getCaptchaCreator(req.getCreator()).generate(CaptchaTypeEnum.getCaptchaTypeEnum(req.getType()));
    }

    @Override
    public Boolean matching(ImageCaptchaCheckReq req) {
        Assert.isTrue(this.captchaCodeFactory.getCaptchaCreator(req.getCreator()).check(req.getId(), req.getCaptchaTrack()), () -> new StarException(StarError.IMAGE_CAPTCHA_CHECK_ERROR));
        return Boolean.TRUE;
    }
}
