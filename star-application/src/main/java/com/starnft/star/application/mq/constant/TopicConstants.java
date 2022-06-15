package com.starnft.star.application.mq.constant;


public enum TopicConstants {

    //消息推送未读消息tag
    NOTICE_UNREAD_DESTINATION("STAR-NOTICE:%s", "unread"),

    //充值topic & tag
    WALLET_RECHARGE_DESTINATION("STAR-RECHARGE:%s", "callback"),

    //商品秒杀下单 topic & tag
    ORDER_SEC_KILL_DESTINATION("STAR-SEC-KILL:%s", "ordered"),
    ;

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
