package com.star.nft.test;

import com.starnft.star.common.template.TemplateHelper;
import com.starnft.star.interfaces.StarApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {StarApplication.class})
@Slf4j
public class TestSms {
    @Resource
    TemplateHelper templateHelper;

}
