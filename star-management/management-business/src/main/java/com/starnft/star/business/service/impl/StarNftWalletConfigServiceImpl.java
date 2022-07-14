package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftWalletConfig;
import com.starnft.star.business.mapper.StarNftWalletConfigMapper;
import com.starnft.star.business.service.IStarNftWalletConfigService;
import com.starnft.star.common.core.domain.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 钱包规则配置Service业务层处理
 *
 * @author shellya
 * @date 2022-06-08
 */
@Service
public class StarNftWalletConfigServiceImpl implements IStarNftWalletConfigService
{
    @Autowired
    private StarNftWalletConfigMapper starNftWalletConfigMapper;

    /**
     * 查询钱包规则配置
     *
     * @param id 钱包规则配置主键
     * @return 钱包规则配置
     */
    @Override
    public StarNftWalletConfig selectStarNftWalletConfigById(Long id)
    {
        return starNftWalletConfigMapper.selectStarNftWalletConfigById(id);
    }

    /**
     * 查询钱包规则配置列表
     *
     * @param starNftWalletConfig 钱包规则配置
     * @return 钱包规则配置
     */
    @Override
    public List<StarNftWalletConfig> selectStarNftWalletConfigList(StarNftWalletConfig starNftWalletConfig)
    {
        return starNftWalletConfigMapper.selectStarNftWalletConfigList(starNftWalletConfig);
    }

    /**
     * 新增钱包规则配置
     *
     * @param starNftWalletConfig 钱包规则配置
     * @return 结果
     */
    @Override
    public int insertStarNftWalletConfig(StarNftWalletConfig starNftWalletConfig, LoginUser loginUser)
    {
        starNftWalletConfig.setCreatedBy(loginUser.getUsername());
        starNftWalletConfig.setModifiedBy(loginUser.getUsername());
        starNftWalletConfig.setCreatedAt(new Date());
        starNftWalletConfig.setModifiedAt(new Date());
        return starNftWalletConfigMapper.insertStarNftWalletConfig(starNftWalletConfig);
    }

    /**
     * 修改钱包规则配置
     *
     * @param starNftWalletConfig 钱包规则配置
     * @return 结果
     */
    @Override
    public int updateStarNftWalletConfig(StarNftWalletConfig starNftWalletConfig, LoginUser loginUser)
    {
        starNftWalletConfig.setModifiedBy(loginUser.getUserId().toString());
        starNftWalletConfig.setModifiedAt(new Date());
        // TODO: 2022/6/12
        return starNftWalletConfigMapper.updateStarNftWalletConfig(starNftWalletConfig);
    }

    /**
     * 批量删除钱包规则配置
     *
     * @param ids 需要删除的钱包规则配置主键
     * @return 结果
     */
    @Override
    public int deleteStarNftWalletConfigByIds(Long[] ids)
    {
        return starNftWalletConfigMapper.deleteStarNftWalletConfigByIds(ids);
    }

    /**
     * 删除钱包规则配置信息
     *
     * @param id 钱包规则配置主键
     * @return 结果
     */
    @Override
    public int deleteStarNftWalletConfigById(Long id)
    {
        return starNftWalletConfigMapper.deleteStarNftWalletConfigById(id);
    }
}
