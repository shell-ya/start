package com.starnft.star.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.bulletin.model.dto.BulletinPageDto;
import com.starnft.star.domain.bulletin.model.vo.BulletinVo;
import com.starnft.star.domain.bulletin.repository.IBulletinRepository;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.infrastructure.entity.BaseEntity;
import com.starnft.star.infrastructure.entity.bulletin.StarBulletin;
import com.starnft.star.infrastructure.mapper.bulletin.StarBulletinMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Date 2022/7/25 6:56 PM
 * @Author ： shellya
 */
@Repository
public class BulletinRepository implements IBulletinRepository {

    @Resource
    StarBulletinMapper bulletinMapper;


    @Override
    public ResponsePageResult<BulletinVo> pageBulletin(BulletinPageDto dto) {
        LambdaQueryWrapper<StarBulletin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseEntity::getIsDeleted, Boolean.FALSE);
        wrapper.eq(Objects.nonNull(dto.getBulletinType()),StarBulletin::getBulletinType,dto.getBulletinType());
        wrapper.orderByDesc(StarBulletin::getPublishTime);
        PageInfo<StarBulletin> pageInfo = PageHelper.startPage(dto.getPage(), dto.getSize()).doSelectPageInfo(() -> this.bulletinMapper.selectList(wrapper));
        List<BulletinVo> bulletinVos = coverBulletinVo(pageInfo.getList());
        return new ResponsePageResult<>(bulletinVos,dto.getPage(), dto.getSize(),pageInfo.getTotal());
    }

    @Override
    public BulletinVo queryBulletinById(Long id) {
        LambdaQueryWrapper<StarBulletin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StarBulletin::getId,id);
        wrapper.eq(BaseEntity::getIsDeleted,Boolean.FALSE);
        StarBulletin bulletin = this.bulletinMapper.selectOne(wrapper);
        if (Objects.isNull(bulletin)) return null;
        return BeanColverUtil.colver(bulletin,BulletinVo.class);
    }

    private List<BulletinVo> coverBulletinVo(List<StarBulletin> starBulletins){
        List<BulletinVo> bulletinVos = new ArrayList<>();
        for (StarBulletin bulletin : starBulletins) {
            BulletinVo bulletinVo = new BulletinVo();
            bulletinVo.setId(bulletin.getId());
            bulletinVo.setTitle(bulletin.getTitle());
            bulletinVo.setPublishTime(bulletin.getPublishTime());
            bulletinVo.setLinkType(bulletin.getLinkType());
            bulletinVo.setLinkUrl(bulletin.getLinkUrl());
            bulletinVos.add(bulletinVo);
        }
        return bulletinVos;
    }
}
