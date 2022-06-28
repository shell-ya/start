package com.starnft.star.application.process.notification.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class NotificationVO {

    /**
     * 被通知uid
     */
    private String toUid;
    /**
     * 通知标题
     */
    private String title;
    /**
     * 通知简介
     */
    private String intro;
    /**
     * 通知时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date sendTime;
    /**
     * 通知缩略图地址
     */
    private String thumbnail;
    /**
     * 通知内容
     */
    private String content;
}
