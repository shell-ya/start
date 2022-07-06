package com.starnft.star.application.process.article.impl;

import com.starnft.star.application.process.article.IUserArticleCore;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveNumbersReq;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.req.UserHaveThemeReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
import com.starnft.star.domain.article.model.vo.UserThemeVO;
import com.starnft.star.domain.article.service.UserThemeService;
import com.starnft.star.domain.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Harlan
 * @date 2022/07/06 13:39
 */
@Service
@RequiredArgsConstructor
public class UserArticleCoreImpl implements IUserArticleCore {
    private final UserThemeService userThemeService;
    private final ThemeService themeService;

    @Override
    public ResponsePageResult<UserSeriesVO> obtainUserArticleSeriesInfo(UserHaveSeriesReq userHaveSeriesReq) {
        return userThemeService.queryUserArticleSeriesInfo(userHaveSeriesReq);
    }

    @Override
    public ResponsePageResult<UserThemeVO> obtainUserArticleThemeInfo(UserHaveThemeReq userHaveThemeReq) {
        return userThemeService.queryUserArticleThemeInfo(userHaveThemeReq);
    }

    @Override
    public ResponsePageResult<UserNumbersVO> obtainUserArticleNumberInfo(UserHaveNumbersReq userHaveNumbersReq) {
        ResponsePageResult<UserNumbersVO> result = userThemeService.queryUserArticleNumberInfo(userHaveNumbersReq);
        Map<Long, Integer> issuedQtyCache = new HashMap<>();
        result.getList().forEach(numberVO -> numberVO.setIssuedQty(
                Optional.ofNullable(issuedQtyCache.get(numberVO.getThemeId())).orElseGet(() -> {
                    Integer qty = this.themeService.obtainThemeIssuedQty(numberVO.getThemeId());
                    issuedQtyCache.put(numberVO.getThemeId(), qty);
                    return qty;
                })));
        return result;
    }
}
