package com.starnft.star.domain.article.model.req;

import com.starnft.star.common.page.RequestPage;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserHaveThemeReq  extends RequestPage {
    private Long seriesId;
    //用户id
    private String userId;
    //类型
    private String themeType;
    //状态
    private String themeStatus;
}