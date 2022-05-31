package com.starnft.star.application.mq.constant;


public enum TopicConstants {

    //消息推送未读消息tag
    NOTICE_UNREAD_DESTINATION("STAR-NOTICE:%s", "unread"),

    WALLET_RECHARGE_DESTINATION("STAR-RECHARGE:%s", "callback");


    private String format;
    private String tag;

    TopicConstants(String format, String tag) {
        this.format = format;
        this.tag = tag;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


}
