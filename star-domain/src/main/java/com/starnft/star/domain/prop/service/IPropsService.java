package com.starnft.star.domain.prop.service;

import com.starnft.star.domain.prop.model.req.PropsConsumptionReq;
import com.starnft.star.domain.prop.model.req.PropsListReq;
import com.starnft.star.domain.prop.model.res.PropsListRes;
import com.starnft.star.domain.prop.model.vo.PropsRelationVO;
import com.starnft.star.domain.prop.model.vo.PropsVO;

import java.util.List;

public interface IPropsService {

    List<PropsListRes> obtainProps(PropsListReq req);

    PropsRelationVO specificProp(Long uid, Long propId);

    PropsVO queryPropsDetails(Long propsId);

    List<PropsVO> propsList();

    Boolean propsConsumption(PropsConsumptionReq req);
}
