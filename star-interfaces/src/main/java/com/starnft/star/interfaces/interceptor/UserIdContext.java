package com.starnft.star.interfaces.interceptor;

import com.starnft.star.common.constant.StarConstants;

import java.util.HashMap;
import java.util.Map;
/**
 * @author WeiChunLAI
 * @date 2022/5/19 1:53
 */
public class UserIdContext {

    public static ThreadLocal<UserId> threadLocal = new ThreadLocal();

    public static void setUserId(UserId userId){
        threadLocal.set(userId);
    }

    public static UserId getUserId(){
        return threadLocal.get();
    }

    public static void removeUserId(){
        threadLocal.remove();
    }
}
