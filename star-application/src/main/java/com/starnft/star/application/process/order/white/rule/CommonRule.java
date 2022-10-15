package com.starnft.star.application.process.order.white.rule;

import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.user.model.vo.WhiteListConfigVO;
import com.starnft.star.domain.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component("commonRule")
@Slf4j
public class CommonRule extends AbstractWhiteRule {

    @Resource
    IUserService service;

    @Override
    public boolean verifyRule(Long uid, Long themeId) {
        //主题优先购
        WhiteListConfigVO whiteListConfigVO = service.obtainWhiteConfig(themeId);
        //通用优先购
        WhiteListConfigVO commonWhiteListConfig = service.obtainWhiteConfig(1001L);

        if (whiteListConfigVO == null && commonWhiteListConfig == null) {
            log.info("商品： [{}] 未配置白名单！", themeId);
            return false;
        }

        Date startTime = whiteListConfigVO.getStartTime();
        Date endTime = whiteListConfigVO.getEndTime();
        Date now = new Date();
        if (now.before(startTime) || now.after(endTime)) {
            throw new StarException(StarError.GOODS_DO_NOT_START_ERROR, "不在优先购可购买时间内");
        }
        Boolean white = service.isWhite(uid, whiteListConfigVO.getWhiteType());
        if (!white) {
            return service.isWhite(uid, commonWhiteListConfig.getWhiteType());
        }
        return true;
    }
}
