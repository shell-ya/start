package com.starnft.star.domain.support.mq.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageLogVO {

    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息id
     */
    private String messageId;
    /**
     * 消息状态
     */
    private String status;
    /**
     * 消息体
     */
    private String messageBody;
    /**
     * 终端
     */
    private String terminate;
}
