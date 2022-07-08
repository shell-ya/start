package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.prop.model.req.PropsListReq;
import com.starnft.star.domain.prop.model.res.PropsListRes;
import com.starnft.star.domain.prop.model.vo.PropsVO;
import com.starnft.star.domain.prop.repository.IPropsRepository;
import com.starnft.star.infrastructure.entity.prop.StarNftPropInfo;
import com.starnft.star.infrastructure.entity.prop.StarNftPropRelation;
import com.starnft.star.infrastructure.mapper.prop.StarNftPropInfoMapper;
import com.starnft.star.infrastructure.mapper.prop.StarNftPropRelationMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PropsRepository implements IPropsRepository {

    @Resource
    private StarNftPropInfoMapper starNftPropInfoMapper;

    @Resource
    private StarNftPropRelationMapper starNftPropRelationMapper;

    @Override
    public List<PropsListRes> obtainProps(PropsListReq req) {
        List<StarNftPropRelation> props = starNftPropRelationMapper.selectList(
                new QueryWrapper<StarNftPropRelation>().eq("uid", req.getUserId()));

        return BeanColverUtil.colverList(props, PropsListRes.class);
    }

    @Override
    public PropsVO queryPropsDetails(Long propsId) {
        StarNftPropInfo starNftPropInfo = starNftPropInfoMapper.selectById(propsId);
        return BeanColverUtil.colver(starNftPropInfo, PropsVO.class);
    }

    @Override
    public List<PropsVO> propsList() {
        return BeanColverUtil.colverList(starNftPropInfoMapper.queryAll(), PropsVO.class);
    }
}
