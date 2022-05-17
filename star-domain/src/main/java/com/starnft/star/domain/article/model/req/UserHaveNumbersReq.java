package com.starnft.star.domain.article.model.req;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
@Data
@SuperBuilder
public class UserHaveNumbersReq implements Serializable {

    private Long themeId;
    //用户id
    private String userId;
    //类型
    private String themeType;
    //状态
    private String themeStatus;
}
