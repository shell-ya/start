package com.starnft.star.interfaces.util;

import org.apache.tomcat.util.security.MD5Encoder;


public class MD5Util {

    /**
     *
     * @param str
     * @return
     */
    public static String encryptMD5(String str){
      return   MD5Encoder.encode(str.getBytes());
    }
}
