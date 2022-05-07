package com.starnft.star.application.process.user.req;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserGatheringInfoReq implements Serializable {

    @NotNull(message = "用户ID不能为空")
    private Long uid;

    public UserGatheringInfoReq(Long uid) {
        this.uid = uid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
