package com.starnft.star.domain.captcha.service.factory;

import com.starnft.star.common.enums.CaptchaCreatorEnum;
import com.starnft.star.common.utils.SpringUtil;
import com.starnft.star.domain.captcha.service.ICaptchaCreator;
import org.springframework.stereotype.Component;

@Component
public class CaptchaCodeFactory {
    public ICaptchaCreator getCaptchaCreator(Integer creator) {
        CaptchaCreatorEnum strategyEnum = CaptchaCreatorEnum.getCaptchaCreatorEnum(creator);
        return SpringUtil.getBean(strategyEnum.getCreator(), ICaptchaCreator.class);
    }
}