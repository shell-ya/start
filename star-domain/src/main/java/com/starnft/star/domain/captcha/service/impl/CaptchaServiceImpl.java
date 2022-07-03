package com.starnft.star.domain.captcha.service.impl;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.enums.CaptchaTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.captcha.model.req.ImageCaptchaCheckReq;
import com.starnft.star.domain.captcha.model.req.ImageCaptchaGenReq;
import com.starnft.star.domain.captcha.model.vo.StarImageCaptchaVO;
import com.starnft.star.domain.captcha.service.ICaptchaService;
import com.starnft.star.domain.captcha.service.factory.CaptchaCodeFactory;
import com.starnft.star.domain.component.RedisUtil;
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
    private final RedisUtil redisUtil;

    @Override
    public StarImageCaptchaVO generateCaptcha(ImageCaptchaGenReq req) {
        return this.captchaCodeFactory.getCaptchaCreator(req.getCreator()).generate(CaptchaTypeEnum.getCaptchaTypeEnum(req.getType()));
    }

    @Override
    public String matching(ImageCaptchaCheckReq req) {
        Assert.isTrue(this.captchaCodeFactory.getCaptchaCreator(req.getCreator()).check(req.getId(), req.getCaptchaTrack()), () -> new StarException(StarError.IMAGE_CAPTCHA_CHECK_ERROR));
        // 保存校验成功标识
        String redisKey = String.format(RedisKey.REDIS_IMAGE_CAPTCHA_CHECK_SUCCESS_TOKEN.getKey(), req.getId());
        this.redisUtil.set(redisKey, req.getId(), RedisKey.REDIS_IMAGE_CAPTCHA_CHECK_SUCCESS_TOKEN.getTime(), RedisKey.REDIS_IMAGE_CAPTCHA_CHECK_SUCCESS_TOKEN.getTimeUnit());
        return req.getId();
    }
}
