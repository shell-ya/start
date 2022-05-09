package com.starnft.star.domain.user.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author WeiChunLAI
 */
@Data
public class UserPwdChangeLogsVO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 历史密码
     */
    private List<String> passwords;
}
