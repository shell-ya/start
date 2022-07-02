package com.starnft.star.domain.captcha.service;

import com.starnft.star.domain.captcha.model.req.ImageCaptchaCheckReq;
import com.starnft.star.domain.captcha.model.req.ImageCaptchaGenReq;
import com.starnft.star.domain.captcha.model.vo.StarImageCaptchaVO;

/**
 * @author Harlan
 * @date 2022/05/30 20:19
 */
public interface ICaptchaService {

    StarImageCaptchaVO generateCaptcha(ImageCaptchaGenReq req);

    String matching(ImageCaptchaCheckReq req);
}
