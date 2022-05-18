package com.starnft.star.infrastructure.entity.message;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "异常消息记录表",description = "")
@TableName("star_nft_message_log")
public class StarNftMessageLog extends BaseEntity {

    @TableId
    private Long id;
    /** 消息主题 */
    @ApiModelProperty(name = "消息主题",notes = "")
    private String topic ;
    /** 消息id */
    @ApiModelProperty(name = "消息id",notes = "")
    private String messageId ;
    /** 消息状态 */
    @ApiModelProperty(name = "消息状态",notes = "")
    private String status ;
    /** 消息体 */
    @ApiModelProperty(name = "消息体",notes = "")
    private String messageBody ;
    /** 终端 */
    @ApiModelProperty(name = "终端",notes = "")
    private String terminate ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getTerminate() {
        return terminate;
    }

    public void setTerminate(String terminate) {
        this.terminate = terminate;
    }
}
