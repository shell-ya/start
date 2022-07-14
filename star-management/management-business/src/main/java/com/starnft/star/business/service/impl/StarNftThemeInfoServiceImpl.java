package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.domain.vo.StarNftThemeInfoVo;
import com.starnft.star.business.domain.vo.ThemeInfoVo;
import com.starnft.star.business.mapper.StarNftThemeInfoMapper;
import com.starnft.star.business.service.IStarNftThemeInfoService;
import com.starnft.star.common.utils.SnowflakeWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 主题Service业务层处理
 *
 * @author shellya
 * @date 2022-06-03
 */
@Service
public class StarNftThemeInfoServiceImpl implements IStarNftThemeInfoService
{
    @Autowired
    private StarNftThemeInfoMapper starNftThemeInfoMapper;

    /**
     * 查询主题
     *
     * @param id 主题主键
     * @return 主题
     */
    @Override
    public StarNftThemeInfo selectStarNftThemeInfoById(Long id)
    {
        return starNftThemeInfoMapper.selectStarNftThemeInfoById(id);
    }

    /**
     * 查询主题列表
     *
     * @param starNftThemeInfo 主题
     * @return 主题
     */
    @Override
    public List<StarNftThemeInfo> selectStarNftThemeInfoList(StarNftThemeInfo starNftThemeInfo)
    {
        return starNftThemeInfoMapper.selectStarNftThemeInfoList(starNftThemeInfo);
    }

    /**
     * 新增主题
     *
     * @param starNftThemeInfo 主题
     * @return 结果
     */
    @Override
    public int insertStarNftThemeInfo(StarNftThemeInfo starNftThemeInfo, Long userId)
    {

        Long themeInfoId = SnowflakeWorker.generateId();
        starNftThemeInfo.setId(themeInfoId);
        starNftThemeInfo.setCreateBy(userId.toString());
        starNftThemeInfo.setUpdateBy(userId.toString());
        starNftThemeInfo.setCreateAt(new Date());
        starNftThemeInfo.setUpdateAt(new Date());
        starNftThemeInfo.setIsDelete(0);
        if (null == starNftThemeInfo.getPublisherId()) starNftThemeInfo.setPublisherId(userId);
        return starNftThemeInfoMapper.insertStarNftThemeInfo(starNftThemeInfo);
    }

    /**
     * 修改主题
     *
     * @param starNftThemeInfo 主题
     * @return 结果
     */
    @Override
    public int updateStarNftThemeInfo(StarNftThemeInfo starNftThemeInfo)
    {
        return starNftThemeInfoMapper.updateStarNftThemeInfo(starNftThemeInfo);
    }

    /**
     * 批量删除主题
     *
     * @param ids 需要删除的主题主键
     * @return 结果
     */
    @Override
    public int deleteStarNftThemeInfoByIds(Long[] ids)
    {
        return starNftThemeInfoMapper.deleteStarNftThemeInfoByIds(ids);
    }

    /**
     * 删除主题信息
     *
     * @param id 主题主键
     * @return 结果
     */
    @Override
    public int deleteStarNftThemeInfoById(Long id)
    {
        return starNftThemeInfoMapper.deleteStarNftThemeInfoById(id);
    }

    @Override
    public List<ThemeInfoVo> selectThemeInfoByPublisherId(Long publisherId) {
        return starNftThemeInfoMapper.selectThemeInfoByPublisherId(publisherId);
    }

    @Override
    public List<StarNftThemeInfoVo> selectStarNftThemeInfoVoList(StarNftThemeInfo starNftThemeInfo) {
        return starNftThemeInfoMapper.selectStarNftThemeInfoVoList(starNftThemeInfo);
    }
}
