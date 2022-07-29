package com.starnft.star.common.chain.utils;

import cn.hutool.crypto.SecureUtil;

public class UserKeyUtils {
    private static final String salt="lywcscpt";
    public  static String keys(String payPassword){
       return SecureUtil.sha1(payPassword.concat(salt));
    }
}
