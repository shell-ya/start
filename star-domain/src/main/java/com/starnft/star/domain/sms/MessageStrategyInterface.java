package com.starnft.star.domain.sms;

public interface MessageStrategyInterface {
    public boolean pullCheckCode(String mobile,String code);
//    public boolean pullExtend(String mobile,String message);
}
