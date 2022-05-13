package com.starnft.star.domain.sms;

import cn.hutool.core.text.StrFormatter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component("swMessageStrategy")
public class SwMessageStrategy implements MessageStrategyInterface {
    @Resource
    SwMessageDistributor swMessageDistributor;
    @Override
    public boolean pullCheckCode(String mobile, String code) {
        String format = StrFormatter.format(check_code_template, code, 5);
        return swMessageDistributor.delivery(mobile,format);
    }
    private final String check_code_template="你的验证码为：{},有效时间为{}分钟。";
}
