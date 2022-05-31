package com.starnft.star.domain.captcha.service;

import com.starnft.star.common.enums.CaptchaTypeEnum;
import com.starnft.star.domain.captcha.model.req.StarImageCaptchaTrack;
import com.starnft.star.domain.captcha.model.vo.StarImageCaptchaVO;

/**
 * @author Harlan
 * @date 2022/05/30 21:58
 */
public interface ICaptchaCreator {
    StarImageCaptchaVO generate(CaptchaTypeEnum typeEnum);

    Boolean check(String id, StarImageCaptchaTrack req);
}
