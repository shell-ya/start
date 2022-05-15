package com.starnft.star.management.utils;

import com.starnft.star.management.model.dto.SysUserDto;
import org.springframework.core.NamedThreadLocal;

public class UserContextHolder {
    private static final ThreadLocal<SysUserDto> userIdResources = new NamedThreadLocal<>("UserContextHolder");

    /**
     * 获取当前线程UserID
     * @return userID
     */
    public static SysUserDto getCurrUser() {
        return userIdResources.get();
    }


    /**
     * 写入当前线程UserID
     * @return userID
     */
    public static void setCurrUser(SysUserDto userDTO) {
        clear();
        userIdResources.set(userDTO);
    }


    /**
     * 清除方法
     * @return userID
     */
    public static void clear() {
        userIdResources.remove();
    }

}
