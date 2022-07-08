package com.starnft.star.domain.prop.repository;


import com.starnft.star.domain.prop.model.req.PropsListReq;
import com.starnft.star.domain.prop.model.res.PropsListRes;
import com.starnft.star.domain.prop.model.vo.PropsVO;

import java.util.List;

public interface IPropsRepository {

    List<PropsListRes> obtainProps(PropsListReq req);

    PropsVO queryPropsDetails(Long propsId);

    List<PropsVO> propsList();
}
