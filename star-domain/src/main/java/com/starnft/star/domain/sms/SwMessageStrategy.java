package com.starnft.star.domain.sms;

import cn.hutool.core.text.StrFormatter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component("swMessageStrategy")
public class SwMessageStrategy implements MessageStrategyInterface {
    @Resource
    SwMessageDistributor swMessageDistributor;
    @Override
    public boolean checkCodeMessage(String mobile, String code) {
        String format = StrFormatter.format(SmsTemplate.check_code_template, code, 5);
        return swMessageDistributor.delivery(mobile,format);
    }

}
