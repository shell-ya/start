package com.starnft.star.application.process.given;

import com.starnft.star.domain.given.model.req.GivenMangeReq;

public interface IGivenCore {
    Boolean giving(Long userId, GivenMangeReq givenMangeReq);
}
