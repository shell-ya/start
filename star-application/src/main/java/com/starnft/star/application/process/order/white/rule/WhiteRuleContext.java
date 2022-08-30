package com.starnft.star.application.process.order.white.rule;

import com.starnft.star.common.utils.SpringUtil;
import com.starnft.star.domain.component.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class WhiteRuleContext {

    @Resource
    private RedisUtil redisUtil;

    private String cacheKey = "star-service:white";

    public void loadRuleClass(String themeId, String className) {
        if (!redisUtil.hHasKey(cacheKey, themeId)) {
            redisUtil.hset(cacheKey, themeId, className);
        }
    }

    public IWhiteRule obtainWhiteRule(String themeId) {
        Object ruleName = redisUtil.hget(cacheKey, themeId);
        if (ruleName == null) {
            ruleName = "commonRule";
        }
        return (IWhiteRule) SpringUtil.getBean(ruleName.toString());
    }

    @PostConstruct
    public void initialize() {
//        //创世
//        loadRuleClass("1002285892654821376", "worldCreationWhiteRule");
//        //收发白羊
//        loadRuleClass("1002287375767941120", "worldCreationWhiteRule");
        loadRuleClass("1002287375767941120", "worldCreationWhiteRule");
        loadRuleClass("1004108446601228288", "worldCreationWhiteRule");
        loadRuleClass("1004832935944343552", "worldCreationWhiteRule");
        loadRuleClass("1004370545875394560", "worldCreationWhiteRule");
        loadRuleClass("1005542994038874112", "worldCreationWhiteRule");
        loadRuleClass("1006211030410809344", "worldCreationWhiteRule");
    }


}
