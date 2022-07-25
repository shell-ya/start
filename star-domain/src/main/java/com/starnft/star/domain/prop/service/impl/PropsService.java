package com.starnft.star.domain.prop.service.impl;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.prop.model.req.PropShopListReq;
import com.starnft.star.domain.prop.model.req.PropsConsumptionReq;
import com.starnft.star.domain.prop.model.req.PropsDeliveryReq;
import com.starnft.star.domain.prop.model.req.PropsListReq;
import com.starnft.star.domain.prop.model.res.PropsListRes;
import com.starnft.star.domain.prop.model.vo.PropsRecordVO;
import com.starnft.star.domain.prop.model.vo.PropsRelationVO;
import com.starnft.star.domain.prop.model.vo.PropsVO;
import com.starnft.star.domain.prop.repository.IPropsRepository;
import com.starnft.star.domain.prop.service.IPropsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PropsService implements IPropsService {

    @Resource
    private IPropsRepository propsRepository;

    @Override
    public List<PropsListRes> obtainProps(PropsListReq req) {
        return propsRepository.obtainProps(req);
    }

    @Override
    public PropsRelationVO specificProp(Long uid, Long propId) {
        return propsRepository.specificProps(uid, propId);
    }

    @Override
    public PropsVO queryPropsDetails(Long propsId) {
        return propsRepository.queryPropsDetails(propsId);
    }

    @Override
    public List<PropsVO> propsList() {
        return propsRepository.propsList();
    }

    @Override
    public ResponsePageResult<PropsVO> propsShopList(PropShopListReq propShopListReq) {
        return propsRepository.propsShopList(propShopListReq);
    }

    @Override
    public Boolean propsConsumption(PropsConsumptionReq req) {

        //执行道具使用逻辑
        req.getInvoker().run();
        Boolean isSuccess = false;
        if (req.getPropsRelationVO().getPropType().equals(StarConstants.PropsType.CONSUMPTION.getCode())) {
            isSuccess = propsRepository.modifyPropsNums(req.getPropsRelationVO().getId(), req.getUserId(), req.getPropId(), req.getPropsRelationVO().getPropCounts() - 1);
        }

        return isSuccess;
    }

    @Override
    public Boolean propsDelivery(PropsDeliveryReq propsDeliveryReq) {

        return propsRepository.propsDelivery(propsDeliveryReq);
    }

    @Override
    public Boolean propsPurchaseRecordGenerated(PropsRecordVO propsRecordVO) {
        return propsRepository.propsPurchaseRecordGenerated(propsRecordVO);
    }
}
