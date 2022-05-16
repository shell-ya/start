package com.starnft.star.interfaces.controller.user;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.vo.UserThemeVO;
import com.starnft.star.domain.article.service.UserThemeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@RequestMapping("/article")
public class UserArticleController {
    @Resource
    private UserThemeService userThemeService;

    @ApiOperation("个人系列")
    @PostMapping("/series")
    public RopResponse<ResponsePageResult<UserThemeVO>> series(@RequestBody UserHaveSeriesReq req) {
        return RopResponse.success(userThemeService.queryUserArticleSeriesInfo(req));
    }
}
