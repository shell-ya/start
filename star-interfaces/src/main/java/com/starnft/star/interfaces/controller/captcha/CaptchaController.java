package com.starnft.star.interfaces.controller.captcha;

import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.captcha.model.req.ImageCaptchaCheckReq;
import com.starnft.star.domain.captcha.model.req.ImageCaptchaGenReq;
import com.starnft.star.domain.captcha.model.vo.StarImageCaptchaVO;
import com.starnft.star.domain.captcha.service.ICaptchaService;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Harlan
 * @date 2022/05/30 19:30
 */
@RestController
@Api(tags = "图像验证码相关接口「CaptchaController」")
@RequestMapping("/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    private final ICaptchaService captchaService;

    @ApiOperation("生成验证码")
    @GetMapping("/generate")
    @TokenIgnore
    public RopResponse<StarImageCaptchaVO> generateCaptcha(ImageCaptchaGenReq req) {
        return RopResponse.success(this.captchaService.generateCaptcha(req));
    }

    @ApiOperation("校验验证码")
    @PostMapping("/check")
    @TokenIgnore
    public RopResponse<Boolean> checkCaptcha(@Validated @RequestBody ImageCaptchaCheckReq req) {
        return RopResponse.success(this.captchaService.matching(req));
    }

}
