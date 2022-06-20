package com.starnft.star.infrastructure.repository;

import com.starnft.star.domain.support.mq.IMessageLogRepository;
import com.starnft.star.domain.support.mq.model.MessageLogVO;
import com.starnft.star.infrastructure.entity.message.StarNftMessageLog;
import com.starnft.star.infrastructure.mapper.message.StarNftMessageLogMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Repository
public class MessageLogRepository implements IMessageLogRepository {

    @Resource
    private StarNftMessageLogMapper starNftMessageLogMapper;

    @Override
    @Transactional
    public boolean writeLog(MessageLogVO messageLogVO) {
        StarNftMessageLog starNftMessageLog = new StarNftMessageLog();
        BeanUtils.copyProperties(messageLogVO, starNftMessageLog);
        starNftMessageLog.setIsDeleted(Boolean.FALSE);
        starNftMessageLog.setCreatedAt(new Date());
        starNftMessageLog.setCreatedBy(0L);
        return starNftMessageLogMapper.insert(starNftMessageLog) == 1;
    }
}
