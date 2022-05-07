package com.starnft.star.application.process.user;

import com.starnft.star.application.process.user.req.UserGatheringInfoReq;
import com.starnft.star.application.process.user.res.UserGatheringInfoRes;

public interface UserTotalInfoCompose {

    /**
     * 用户汇总信息查询 用户个人信息、钱包信息
     * @param req 获取用户总信息请求
     * @return 获取用户总信息响应
     */
    UserGatheringInfoRes ObtainUserGatheringInfo(UserGatheringInfoReq req);

}
