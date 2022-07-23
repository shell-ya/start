package com.starnft.star.domain.prop.repository;


import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.prop.model.req.PropShopListReq;
import com.starnft.star.domain.prop.model.req.PropsListReq;
import com.starnft.star.domain.prop.model.res.PropsListRes;
import com.starnft.star.domain.prop.model.vo.PropsRelationVO;
import com.starnft.star.domain.prop.model.vo.PropsVO;

import java.util.List;

public interface IPropsRepository {

    List<PropsListRes> obtainProps(PropsListReq req);

    PropsVO queryPropsDetails(Long propsId);

    PropsRelationVO specificProps(Long uid, Long propId);

    Boolean modifyPropsNums(Long id, Long uid, Long propId, Integer counts);

    List<PropsVO> propsList();

    ResponsePageResult<PropsVO> propsShopList(PropShopListReq propShopListReq);
}
