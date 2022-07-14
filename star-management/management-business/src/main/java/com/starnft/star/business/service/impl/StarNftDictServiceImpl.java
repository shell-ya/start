package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftDict;
import com.starnft.star.business.mapper.StarNftDictMapper;
import com.starnft.star.business.service.IStarNftDictService;
import com.starnft.star.common.core.domain.model.LoginUser;
import com.starnft.star.common.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 字典Service业务层处理
 *
 * @author shellya
 * @date 2022-06-08
 */
@Service
public class StarNftDictServiceImpl implements IStarNftDictService
{
    @Autowired
    private StarNftDictMapper starNftDictMapper;

    /**
     * 查询字典
     *
     * @param id 字典主键
     * @return 字典
     */
    @Override
    public StarNftDict selectStarNftDictById(Long id)
    {
        StarNftDict dict = starNftDictMapper.selectStarNftDictById(id);
        if (1 == dict.getDoSecret())dict.setDictCode(AESUtil.decrypt(dict.getDictCode()));
        return dict;
    }

    /**
     * 查询字典列表
     *
     * @param starNftDict 字典
     * @return 字典
     */
    @Override
    public List<StarNftDict> selectStarNftDictList(StarNftDict starNftDict)
    {
        List<StarNftDict> starNftDicts = starNftDictMapper.selectStarNftDictList(starNftDict);
        for (StarNftDict dict : starNftDicts){
            if (1 == dict.getDoSecret()){
                dict.setDictCode(AESUtil.decrypt(dict.getDictCode()));
            }
        }
        return starNftDicts;
    }

    /**
     * 新增字典
     *
     * @param starNftDict 字典
     * @return 结果
     */
    @Override
    public int insertStarNftDict(StarNftDict starNftDict, LoginUser loginUser)
    {
        starNftDict.setCreatedBy(loginUser.getUserId().toString());
        starNftDict.setModifiedBy(loginUser.getUserId().toString());
        starNftDict.setCreatedAt(new Date());
        starNftDict.setModifiedAt(new Date());
        if (1 == starNftDict.getDoSecret()) starNftDict.setDictCode(AESUtil.encrypt(starNftDict.getDictCode()));
        return starNftDictMapper.insertStarNftDict(starNftDict);
    }

    /**
     * 修改字典
     *
     * @param starNftDict 字典
     * @return 结果
     */
    @Override
    public int updateStarNftDict(StarNftDict starNftDict, LoginUser loginUser)
    {
        starNftDict.setModifiedBy(loginUser.getUserId().toString());
        starNftDict.setModifiedAt(new Date());
        if (1 == starNftDict.getDoSecret()) starNftDict.setDictCode(AESUtil.encrypt(starNftDict.getDictCode()));
        return starNftDictMapper.updateStarNftDict(starNftDict);
    }

    /**
     * 批量删除字典
     *
     * @param ids 需要删除的字典主键
     * @return 结果
     */
    @Override
    public int deleteStarNftDictByIds(Long[] ids)
    {
        return starNftDictMapper.deleteStarNftDictByIds(ids);
    }

    /**
     * 删除字典信息
     *
     * @param id 字典主键
     * @return 结果
     */
    @Override
    public int deleteStarNftDictById(Long id)
    {
        return starNftDictMapper.deleteStarNftDictById(id);
    }

    public int createDict(ArrayList<StarNftDict> starNftDicts) {
        for (StarNftDict dict : starNftDicts) {
            //加密存储
            dict.setDictCode(AESUtil.encrypt(dict.getDictCode()));
        }
        return starNftDictMapper.insertBatch(starNftDicts);
    }
}
