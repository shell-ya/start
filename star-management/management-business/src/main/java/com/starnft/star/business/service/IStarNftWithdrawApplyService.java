package com.starnft.star.business.service;

import com.starnft.star.business.domain.StarNftWithdrawApply;
import com.starnft.star.common.core.domain.model.LoginUser;

import java.util.List;

/**
 * 提现申请Service接口
 *
 * @author shellya
 * @date 2022-05-28
 */
public interface IStarNftWithdrawApplyService
{
    /**
     * 查询提现申请
     *
     * @param id 提现申请主键
     * @return 提现申请
     */
    public StarNftWithdrawApply selectStarNftWithdrawApplyById(Long id);

    /**
     * 查询提现申请列表
     *
     * @param starNftWithdrawApply 提现申请
     * @return 提现申请集合
     */
    public List<StarNftWithdrawApply> selectStarNftWithdrawApplyList(StarNftWithdrawApply starNftWithdrawApply);

    /**
     * 新增提现申请
     *
     * @param starNftWithdrawApply 提现申请
     * @return 结果
     */
    public int insertStarNftWithdrawApply(StarNftWithdrawApply starNftWithdrawApply);

    /**
     * 修改提现申请
     *
     * @param starNftWithdrawApply 提现申请
     * @return 结果
     */
    public int updateStarNftWithdrawApply(StarNftWithdrawApply starNftWithdrawApply, LoginUser loginUser);

    /**
     * 批量删除提现申请
     *
     * @param ids 需要删除的提现申请主键集合
     * @return 结果
     */
    public int deleteStarNftWithdrawApplyByIds(Long[] ids);

    /**
     * 删除提现申请信息
     *
     * @param id 提现申请主键
     * @return 结果
     */
    public int deleteStarNftWithdrawApplyById(Long id);
}
