package com.starnft.star.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starnft.star.business.domain.StarNftUserTheme;
import com.starnft.star.business.domain.po.UpdateUserThemeVo;
import com.starnft.star.business.domain.vo.GiveReq;
import com.starnft.star.business.domain.vo.UserInfo;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户藏品Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-20
 */
@Mapper
public interface StarNftUserThemeMapper extends BaseMapper<StarNftUserTheme>
{
    /**
     * 查询用户藏品
     *
     * @param id 用户藏品主键
     * @return 用户藏品
     */
    public StarNftUserTheme selectStarNftUserThemeById(Long id);

    /**
     * 查询用户藏品列表
     *
     * @param starNftUserTheme 用户藏品
     * @return 用户藏品集合
     */
    public List<StarNftUserTheme> selectStarNftUserThemeList(StarNftUserTheme starNftUserTheme);

    /**
     * 新增用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    public int insertStarNftUserTheme(StarNftUserTheme starNftUserTheme);

    /**
     * 修改用户藏品
     *
     * @param starNftUserTheme 用户藏品
     * @return 结果
     */
    public int updateStarNftUserTheme(StarNftUserTheme starNftUserTheme);

    public int updateStarNftUserThemeStatus(UpdateUserThemeVo updateUserThemeVo);

    /**
     * 删除用户藏品
     *
     * @param id 用户藏品主键
     * @return 结果
     */
    public int deleteStarNftUserThemeById(Long id);

    /**
     * 批量删除用户藏品
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStarNftUserThemeByIds(Long[] ids);

//    boolean modifyUserNumberStatus(Long fromUid, Long seriesThemeId, BigDecimal zero, UserNumberStatusEnum purchased, UserNumberStatusEnum givend);

    StarNftUserTheme selectUserThemeBySeriesThemeId(GiveReq giveReq);

    List<UserInfo> selecUsertHasTheme(Long seriesThemeInfoId);
}
