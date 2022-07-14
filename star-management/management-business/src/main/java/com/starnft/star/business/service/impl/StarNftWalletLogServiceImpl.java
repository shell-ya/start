package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftWalletLog;
import com.starnft.star.business.mapper.StarNftWalletLogMapper;
import com.starnft.star.business.service.IStarNftWalletLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 钱包变化记录Service业务层处理
 *
 * @author shellya
 * @date 2022-06-09
 */
@Service
public class StarNftWalletLogServiceImpl implements IStarNftWalletLogService
{
    @Autowired
    private StarNftWalletLogMapper starNftWalletLogMapper;

    /**
     * 查询钱包变化记录
     *
     * @param id 钱包变化记录主键
     * @return 钱包变化记录
     */
    @Override
    public StarNftWalletLog selectStarNftWalletLogById(Long id)
    {
        return starNftWalletLogMapper.selectStarNftWalletLogById(id);
    }

    /**
     * 查询钱包变化记录列表
     *
     * @param starNftWalletLog 钱包变化记录
     * @return 钱包变化记录
     */
    @Override
    public List<StarNftWalletLog> selectStarNftWalletLogList(StarNftWalletLog starNftWalletLog)
    {
        return starNftWalletLogMapper.selectStarNftWalletLogList(starNftWalletLog);
    }

    /**
     * 新增钱包变化记录
     *
     * @param starNftWalletLog 钱包变化记录
     * @return 结果
     */
    @Override
    public int insertStarNftWalletLog(StarNftWalletLog starNftWalletLog)
    {
        return starNftWalletLogMapper.insertStarNftWalletLog(starNftWalletLog);
    }

    /**
     * 修改钱包变化记录
     *
     * @param starNftWalletLog 钱包变化记录
     * @return 结果
     */
    @Override
    public int updateStarNftWalletLog(StarNftWalletLog starNftWalletLog)
    {
        return starNftWalletLogMapper.updateStarNftWalletLog(starNftWalletLog);
    }

    /**
     * 批量删除钱包变化记录
     *
     * @param ids 需要删除的钱包变化记录主键
     * @return 结果
     */
    @Override
    public int deleteStarNftWalletLogByIds(Long[] ids)
    {
        return starNftWalletLogMapper.deleteStarNftWalletLogByIds(ids);
    }

    /**
     * 删除钱包变化记录信息
     *
     * @param id 钱包变化记录主键
     * @return 结果
     */
    @Override
    public int deleteStarNftWalletLogById(Long id)
    {
        return starNftWalletLogMapper.deleteStarNftWalletLogById(id);
    }
}
