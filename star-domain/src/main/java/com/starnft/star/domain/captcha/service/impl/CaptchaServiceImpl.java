package com.starnft.star.domain.captcha.service.impl;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.enums.CaptchaTypeEnum;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.common.utils.RandomUtil;
import com.starnft.star.domain.captcha.helper.NECaptchaVerifier;
import com.starnft.star.domain.captcha.helper.VerifyResult;
import com.starnft.star.domain.captcha.model.req.ImageCaptchaCheckReq;
import com.starnft.star.domain.captcha.model.req.ImageCaptchaGenReq;
import com.starnft.star.domain.captcha.model.req.NetEaseMatchingReq;
import com.starnft.star.domain.captcha.model.vo.StarImageCaptchaVO;
import com.starnft.star.domain.captcha.service.ICaptchaService;
import com.starnft.star.domain.captcha.service.factory.CaptchaCodeFactory;
import com.starnft.star.domain.component.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    private final NECaptchaVerifier neCaptchaVerifier;

    @Override
    public StarImageCaptchaVO generateCaptcha(ImageCaptchaGenReq req) {
        return this.captchaCodeFactory.getCaptchaCreator(req.getCreator()).generate(CaptchaTypeEnum.getCaptchaTypeEnum(req.getType()));
    }

    @Override
    public String matching(ImageCaptchaCheckReq req) {
        Assert.isTrue(this.captchaCodeFactory.getCaptchaCreator(req.getCreator()).check(req.getId(), req.getCaptchaTrack()), () -> new StarException(StarError.IMAGE_CAPTCHA_CHECK_ERROR));
        return saveCaptchaValidFlag(req.getId());
    }

    @Override
    public String matchingNetEase(NetEaseMatchingReq req) {
        VerifyResult result = neCaptchaVerifier.verify(req.getValidate());
        Assert.isTrue(result.isResult(), () -> new StarException(StarError.IMAGE_CAPTCHA_CHECK_ERROR));
        String token = StringUtils.isBlank(result.getToken()) ? RandomUtil.randomString(16) : result.getToken();
        return saveCaptchaValidFlag(token);
    }

    // 保存校验成功标识
    private String saveCaptchaValidFlag(String token) {
        String redisKey = String.format(RedisKey.REDIS_IMAGE_CAPTCHA_CHECK_SUCCESS_TOKEN.getKey(), token);
        this.redisUtil.set(redisKey, token, RedisKey.REDIS_IMAGE_CAPTCHA_CHECK_SUCCESS_TOKEN.getTime(), RedisKey.REDIS_IMAGE_CAPTCHA_CHECK_SUCCESS_TOKEN.getTimeUnit());
        return token;
    }

}
