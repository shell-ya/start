package com.starnft.star.domain.sms;

public interface MessageStrategyInterface {
    public boolean checkCodeMessage(String mobile,String code);
//    public boolean pullExtend(String mobile,String message);
}
