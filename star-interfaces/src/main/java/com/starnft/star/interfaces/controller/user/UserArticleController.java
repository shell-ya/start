package com.starnft.star.interfaces.controller.user;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.article.model.req.UserHaveNumbersReq;
import com.starnft.star.domain.article.model.req.UserHaveSeriesReq;
import com.starnft.star.domain.article.model.req.UserHaveThemeReq;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.model.vo.UserSeriesVO;
import com.starnft.star.domain.article.model.vo.UserThemeVO;
import com.starnft.star.domain.article.service.UserThemeService;
import com.starnft.star.interfaces.interceptor.UserResolverInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/article")
@Api(tags = "用户收藏相关接口「UserArticleController」")
public class UserArticleController {
    @Resource
    private UserThemeService userThemeService;

    @ApiOperation("获取用户收藏的系列")
    @PostMapping("/series")
    public RopResponse<ResponsePageResult<UserSeriesVO>> series(UserResolverInfo userResolverInfo, @RequestBody UserHaveSeriesReq req) {
        req.setUserId(userResolverInfo.getUserId());
        return RopResponse.success(this.userThemeService.queryUserArticleSeriesInfo(req));
    }

    @ApiOperation("获取用户收藏的主题")
    @PostMapping("/themes")
    public RopResponse<ResponsePageResult<UserThemeVO>> theme(UserResolverInfo userResolverInfo, @RequestBody UserHaveThemeReq req) {
        req.setUserId(userResolverInfo.getUserId());
        return RopResponse.success(this.userThemeService.queryUserArticleThemeInfo(req));
    }

    @ApiOperation("获取用户收藏的藏品")
    @PostMapping("/numbers")
    public RopResponse<ResponsePageResult<UserNumbersVO>> numbers(UserResolverInfo userResolverInfo, @RequestBody UserHaveNumbersReq req) {
        req.setUserId(userResolverInfo.getUserId());
        return RopResponse.success(this.userThemeService.queryUserArticleNumberInfo(req));
    }
}
