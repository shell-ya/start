package com.starnft.star.domain.prop.service;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.prop.model.req.PropShopListReq;
import com.starnft.star.domain.prop.model.req.PropsConsumptionReq;
import com.starnft.star.domain.prop.model.req.PropsListReq;
import com.starnft.star.domain.prop.model.res.PropsListRes;
import com.starnft.star.domain.prop.model.vo.PropsRelationVO;
import com.starnft.star.domain.prop.model.vo.PropsVO;

import java.util.List;

public interface IPropsService {

    //获取用户拥有的道具列表
    List<PropsListRes> obtainProps(PropsListReq req);

    //查询某个用户的某个道具 用作存在判断
    PropsRelationVO specificProp(Long uid, Long propId);

    //道具详情
    PropsVO queryPropsDetails(Long propsId);

    //所有道具列表
    List<PropsVO> propsList();

    //道具商城列表
    ResponsePageResult<PropsVO> propsShopList(PropShopListReq propShopListReq);

    //道具使用
    Boolean propsConsumption(PropsConsumptionReq req);
}
