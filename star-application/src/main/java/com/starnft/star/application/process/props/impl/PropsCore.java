package com.starnft.star.application.process.props.impl;

import com.starnft.star.application.process.props.IPropsCore;
import com.starnft.star.application.process.props.invokers.PropsExecutor;
import com.starnft.star.application.process.props.model.req.PropInvokeReq;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.prop.model.req.PropsConsumptionReq;
import com.starnft.star.domain.prop.model.vo.PropsRelationVO;
import com.starnft.star.domain.prop.model.vo.PropsVO;
import com.starnft.star.domain.prop.service.IPropsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PropsCore implements IPropsCore {


    @Resource
    private IPropsService propsService;

    @Resource
    private PropsExecutor propsExecutor;

    @Override
    public Boolean propInvoke(PropInvokeReq req) {

        PropsRelationVO propsRelationVO = propsService.specificProp(req.getUserId(), req.getPropId());

        //道具存在判断
        if (propsRelationVO == null) {
            throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "您未拥有该道具");
        }

        // TODO: 2022/7/9  道具可使用时间判断


        PropsVO propsVO = propsService.queryPropsDetails(req.getPropId());

        Boolean isSuccess = propsService.propsConsumption(PropsConsumptionReq.builder()
                .propId(req.getPropId())
                .userId(req.getUserId())
                .propsRelationVO(propsRelationVO)
                .invoker(() -> propsExecutor.execute(req.getUserId(), propsVO))
                .build());

        return isSuccess;
    }
}
