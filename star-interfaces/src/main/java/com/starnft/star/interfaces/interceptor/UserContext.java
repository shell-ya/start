package com.starnft.star.interfaces.interceptor;

/**
 * @author WeiChunLAI
 * @date 2022/5/19 1:53
 */
public class UserContext {

    public static ThreadLocal<UserResolverInfo> threadLocal = new ThreadLocal();

    public static void setUserResolverInfo(UserResolverInfo userResolverInfo){
        threadLocal.set(userResolverInfo);
    }

    public static UserResolverInfo getUserId(){
        return threadLocal.get();
    }

    public static void removeUserId(){
        threadLocal.remove();
    }
}
