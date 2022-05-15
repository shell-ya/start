package com.starnft.star.domain.article.model.req;

import lombok.Data;

@Data
public class UserHaveSeriesReq {
    //用户id
    private String userId;
    //类型
    private String themeType;
    //状态
    private String themeStatus;

}
