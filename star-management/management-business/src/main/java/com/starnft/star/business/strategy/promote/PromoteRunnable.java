package com.starnft.star.business.strategy.promote;

import cn.hutool.core.util.IdUtil;
import com.starnft.star.business.domain.StarNftPromoteRecord;
import com.starnft.star.business.service.IStarNftPromoteRecordService;
import com.starnft.star.common.enums.PromoteTypeEnums;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PromoteRunnable implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Resource
    IStarNftPromoteRecordService starNftPromoteRecordService;
    @Transactional
  public Boolean running(List<String> partitions,Integer types,String context){
      PromoteStrategy promoteStrategy = applicationContext.getBean(PromoteTypeEnums.getDefaultMessageType(types).getStrategy(), PromoteStrategy.class);
        String orderSn = promoteStrategy.promoteBatch(partitions, context);
        List<StarNftPromoteRecord> collect = partitions.stream().map(partition -> {
            return getStarNftPromoteRecord(types, context, orderSn, partition);
        }).collect(Collectors.toList());
        int i = starNftPromoteRecordService.insertStarNftPromoteRecordBatch(collect);
        return true;
    }

    @NotNull
    private StarNftPromoteRecord getStarNftPromoteRecord(Integer types, String context, String orderSn, String partition) {
        StarNftPromoteRecord starNftPromoteRecord = new StarNftPromoteRecord();
        starNftPromoteRecord.setPromoteType(Long.parseLong(types.toString()));
        starNftPromoteRecord.setContext(context);
        starNftPromoteRecord.setId(IdUtil.getSnowflake().nextId());
        starNftPromoteRecord.setRecipient(partition);
        starNftPromoteRecord.setOrdersn(orderSn);
        starNftPromoteRecord.setSendStatus(0L);
        starNftPromoteRecord.setCreatedAt(new Date());
        starNftPromoteRecord.setModifiedAt(new Date());
        return starNftPromoteRecord;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
