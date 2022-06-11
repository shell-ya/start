package com.starnft.star.infrastructure.repository;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.notify.model.req.NotifyOrderReq;
import com.starnft.star.domain.notify.repository.INotifyOrderRepository;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.infrastructure.entity.notify.StarNftNotifyOrder;
import com.starnft.star.infrastructure.mapper.notify.StarNftNotifyOrderMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository
public class NotifyOrderRepository  implements INotifyOrderRepository {
    @Resource
    StarNftNotifyOrderMapper starNftNotifyOrderMapper;
    @Resource
    private Map<StarConstants.Ids, IIdGenerator> idGeneratorMap;
    @Override
    public Integer insertNotifyOrder(NotifyOrderReq notifyOrderReq) {
        long notifyOrderId = idGeneratorMap.get(StarConstants.Ids.SnowFlake).nextId();
        StarNftNotifyOrder starNftNotifyOrder = new StarNftNotifyOrder( );
        starNftNotifyOrder.setId(notifyOrderId);
        starNftNotifyOrder.setUid(notifyOrderReq.getUid());
        starNftNotifyOrder.setOrderSn(notifyOrderReq.getOrderSn());
        starNftNotifyOrder.setMessage(notifyOrderReq.getMessage());
        starNftNotifyOrder.setStatus(notifyOrderReq.getStatus());
        starNftNotifyOrder.setPayChannel(notifyOrderReq.getPayChannel());
        starNftNotifyOrder.setPayTime(notifyOrderReq.getPayTime());
        starNftNotifyOrder.setCreateTime(notifyOrderReq.getCreateTime());
        starNftNotifyOrder.setTotalAmount(notifyOrderReq.getTotalAmount());
        return  starNftNotifyOrderMapper.insert(starNftNotifyOrder);

    }
}
