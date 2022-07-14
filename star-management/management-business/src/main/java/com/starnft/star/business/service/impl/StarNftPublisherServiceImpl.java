package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftPublisher;
import com.starnft.star.business.domain.vo.PublisherVo;
import com.starnft.star.business.mapper.StarNftPublisherMapper;
import com.starnft.star.business.service.IStarNftPublisherService;
import com.starnft.star.common.constant.YesOrNoStatusEnum;
import com.starnft.star.common.core.domain.TreeSelect;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作室Service业务层处理
 *
 * @author shellya
 * @date 2022-06-26
 */
@Service
public class StarNftPublisherServiceImpl implements IStarNftPublisherService
{
    @Autowired
    private StarNftPublisherMapper starNftPublisherMapper;

    /**
     * 查询工作室
     *
     * @param id 工作室主键
     * @return 工作室
     */
    @Override
    public StarNftPublisher selectStarNftPublisherById(Long id)
    {
        return starNftPublisherMapper.selectStarNftPublisherById(id);
    }

    /**
     * 查询工作室列表
     *
     * @param starNftPublisher 工作室
     * @return 工作室
     */
    @Override
    public List<StarNftPublisher> selectStarNftPublisherList(StarNftPublisher starNftPublisher)
    {
        return starNftPublisherMapper.selectStarNftPublisherList(starNftPublisher);
    }

    /**
     * 新增工作室
     *
     * @param starNftPublisher 工作室
     * @return 结果
     */
    @Override
    public int insertStarNftPublisher(StarNftPublisher starNftPublisher)
    {
        return starNftPublisherMapper.insertStarNftPublisher(starNftPublisher);
    }

    /**
     * 修改工作室
     *
     * @param starNftPublisher 工作室
     * @return 结果
     */
    @Override
    public int updateStarNftPublisher(StarNftPublisher starNftPublisher)
    {
        return starNftPublisherMapper.updateStarNftPublisher(starNftPublisher);
    }

    /**
     * 批量删除工作室
     *
     * @param ids 需要删除的工作室主键
     * @return 结果
     */
    @Override
    public int deleteStarNftPublisherByIds(Long[] ids)
    {
        return starNftPublisherMapper.deleteStarNftPublisherByIds(ids);
    }

    /**
     * 删除工作室信息
     *
     * @param id 工作室主键
     * @return 结果
     */
    @Override
    public int deleteStarNftPublisherById(Long id)
    {
        return starNftPublisherMapper.deleteStarNftPublisherById(id);
    }

    @Override
    public List<PublisherVo> selectAllPublisher() {
        // parent
        List<PublisherVo> publisherVos = starNftPublisherMapper.selectParentPublisher();
        publisherVos.stream().forEach(item -> {
            item.setChildren(starNftPublisherMapper.selectChildrenPublisher(item.getValue()));
        });
        return publisherVos;
    }

    @Override
    public List<TreeSelect> treeSelect(StarNftPublisher starNftPublisher) {
        ArrayList<TreeSelect> result = Lists.newArrayList();
        TreeSelect base = new TreeSelect();
//        base.setId();
        base.setLabel("主目录");
        StarNftPublisher search = new StarNftPublisher();
        search.setIsBase(YesOrNoStatusEnum.YES.getCode());
        List<StarNftPublisher> starNftPublishers = starNftPublisherMapper.selectStarNftPublisherList(search);
        List<TreeSelect> collect = starNftPublishers.stream().map(item -> {
            TreeSelect treeSelect = new TreeSelect();
            treeSelect.setId(item.getId());
            treeSelect.setLabel(item.getPublisher());
            return treeSelect;
        }).collect(Collectors.toList());
        base.setChildren(collect);
        result.add(base);
//        if (!starNftPublishers.isEmpty()){
//            List<Long> ids = collect.stream().map(item -> item.getId()).collect(Collectors.toList());
//            Map<Long, List<TreeSelect>> childrens = starNftPublisherMapper.selectChildrenPublisherByPids(ids).stream().collect(Collectors.groupingBy(StarNftPublisher::getPid, Collectors.mapping(item -> {
//                TreeSelect treeSelect = new TreeSelect();
//                treeSelect.setId(item.getId());
//                treeSelect.setLabel(item.getPublisher());
//                return treeSelect;
//            }, Collectors.toList())));
//            for (TreeSelect treeSelect : collect) {
//                Long id = treeSelect.getId();
//                List<TreeSelect> treeSelects = childrens.get(id);
//                treeSelect.setChildren(treeSelects);
//            }
//        }

        return  result;
    }
}
