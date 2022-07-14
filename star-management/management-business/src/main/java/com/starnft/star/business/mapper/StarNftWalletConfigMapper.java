package com.starnft.star.business.mapper;

import com.starnft.star.business.domain.StarNftWalletConfig;

import java.util.List;

/**
 * 钱包规则配置Mapper接口
 *
 * @author shellya
 * @date 2022-06-08
 */
public interface StarNftWalletConfigMapper
{
    /**
     * 查询钱包规则配置
     *
     * @param id 钱包规则配置主键
     * @return 钱包规则配置
     */
    public StarNftWalletConfig selectStarNftWalletConfigById(Long id);


    /**
     * 查询钱包规则配置
     *
     * @param channel 钱包规则配置渠道
     * @return 钱包规则配置
     */
    public StarNftWalletConfig selectStarNftWalletConfigByChannel(String channel);

    /**
     * 查询钱包规则配置列表
     *
     * @param starNftWalletConfig 钱包规则配置
     * @return 钱包规则配置集合
     */
    public List<StarNftWalletConfig> selectStarNftWalletConfigList(StarNftWalletConfig starNftWalletConfig);

    /**
     * 新增钱包规则配置
     *
     * @param starNftWalletConfig 钱包规则配置
     * @return 结果
     */
    public int insertStarNftWalletConfig(StarNftWalletConfig starNftWalletConfig);

    /**
     * 修改钱包规则配置
     *
     * @param starNftWalletConfig 钱包规则配置
     * @return 结果
     */
    public int updateStarNftWalletConfig(StarNftWalletConfig starNftWalletConfig);

    /**
     * 删除钱包规则配置
     *
     * @param id 钱包规则配置主键
     * @return 结果
     */
    public int deleteStarNftWalletConfigById(Long id);

    /**
     * 批量删除钱包规则配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftWalletConfigByIds(Long[] ids);
}
