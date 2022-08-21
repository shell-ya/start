package com.starnft.star.interfaces.controller.given;

import com.starnft.star.application.process.given.IGivenCore;
import com.starnft.star.common.RopResponse;
import com.starnft.star.domain.given.model.req.GivenMangeReq;
import com.starnft.star.interfaces.interceptor.UserContext;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "转赠「GivenController」")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/given")
public class GivenController {
    @Resource
    IGivenCore givenCore;
    @PostMapping("/givenManage")
    public RopResponse givenManage(@RequestBody GivenMangeReq givenMangeReq){
        Long userId = UserContext.getUserId().getUserId();
     return   RopResponse.success(givenCore.giving(userId,givenMangeReq));
    }
}
