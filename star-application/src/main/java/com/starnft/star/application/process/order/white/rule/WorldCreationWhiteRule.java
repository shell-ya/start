package com.starnft.star.application.process.order.white.rule;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.ImmutableList;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("worldCreationWhiteRule")
public class WorldCreationWhiteRule extends AbstractWhiteRule {

    private final ImmutableList<Long> shouldHave =
            ImmutableList.of(1000489186785472512L, 1000490621916909568L, 1000491126767976448L);

    @Override
    public boolean verifyRule(Long uid, Long themeId) {
        for (Long sh : shouldHave) {
            List<UserNumbersVO> userNumbersVOS = numberCore.checkHasNumber(uid, sh, UserNumberStatusEnum.PURCHASED);
            if (CollectionUtil.isNotEmpty(userNumbersVOS)) {
                return true;
            }
        }
        return false;
    }
}
