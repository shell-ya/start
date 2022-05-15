package com.starnft.star.management.exception;


import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;

public class ExceptionAssert {
    public  static  void assertCondition(boolean condition, StarError starError, String msg){
        if (condition){
            throw new StarException(starError,msg);
        }
    }

}
