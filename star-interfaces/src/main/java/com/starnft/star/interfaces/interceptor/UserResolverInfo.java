package com.starnft.star.interfaces.interceptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WeiChunLAI
 * @date 2022/5/19 1:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResolverInfo {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户手机号
     */
    private String phone;
}
