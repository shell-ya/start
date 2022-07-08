package com.starnft.star.domain.prop.service.impl;

import com.starnft.star.domain.prop.model.req.PropsConsumptionReq;
import com.starnft.star.domain.prop.model.req.PropsListReq;
import com.starnft.star.domain.prop.model.res.PropsListRes;
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
    public PropsVO queryPropsDetails(Long propsId) {
        return propsRepository.queryPropsDetails(propsId);
    }

    @Override
    public List<PropsVO> propsList() {
        return propsRepository.propsList();
    }

    @Override
    public Integer propsConsumption(PropsConsumptionReq req) {

        return null;
    }
}
