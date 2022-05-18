package com.starnft.star.domain.support.mq;

import com.starnft.star.domain.support.mq.model.MessageLogVO;

public interface IMessageLogRepository {

    boolean writeLog(MessageLogVO messageLogVO);
}
