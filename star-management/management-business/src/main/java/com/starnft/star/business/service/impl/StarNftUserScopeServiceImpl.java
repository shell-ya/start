package com.starnft.star.business.service.impl;

import cn.hutool.core.util.IdUtil;
import com.starnft.star.business.domain.StarNftScopeRecord;
import com.starnft.star.business.domain.StarNftUserScope;
import com.starnft.star.business.domain.dto.UserScopeAddDTO;
import com.starnft.star.business.mapper.StarNftScopeRecordMapper;
import com.starnft.star.business.mapper.StarNftUserScopeMapper;
import com.starnft.star.business.service.IStarNftUserScopeService;
import com.starnft.star.common.exception.StarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户积分Service业务层处理
 *
 * @author shellya
 * @date 2022-06-25
 */
@Service
public class StarNftUserScopeServiceImpl implements IStarNftUserScopeService {
    @Autowired
    private StarNftUserScopeMapper starNftUserScopeMapper;
    @Autowired
    private StarNftScopeRecordMapper starNftScopeRecordMapper;
    /**
     * 查询用户积分
     *
     * @param id 用户积分主键
     * @return 用户积分
     */
    @Override
    public StarNftUserScope selectStarNftUserScopeById(Long id) {
        return starNftUserScopeMapper.selectStarNftUserScopeById(id);
    }

    /**
     * 查询用户积分列表
     *
     * @param starNftUserScope 用户积分
     * @return 用户积分
     */
    @Override
    public List<StarNftUserScope> selectStarNftUserScopeList(StarNftUserScope starNftUserScope) {
        return starNftUserScopeMapper.selectStarNftUserScopeList(starNftUserScope);
    }

    /**
     * 新增用户积分
     *
     * @param
     * @return 结果
     */
    @Override
    @Transactional
    public int insertStarNftUserScope(UserScopeAddDTO userScopeAddDTO) {

        StarNftUserScope starNftUserScope = new StarNftUserScope();
        starNftUserScope.setUserId(userScopeAddDTO.getAccount());
        starNftUserScope.setScopeType(userScopeAddDTO.getScopeType());
        StarNftUserScope result = starNftUserScopeMapper.selectStarNftUserScope(starNftUserScope);
        StarNftScopeRecord starNftScopeRecord = new StarNftScopeRecord();
        starNftScopeRecord.setId(IdUtil.getSnowflake().nextId());
        starNftScopeRecord.setScope(userScopeAddDTO.getUserScope());
        starNftScopeRecord.setMold(1L);
        starNftScopeRecord.setScopeType(userScopeAddDTO.getScopeType());
        starNftScopeRecord.setUserId(userScopeAddDTO.getAccount());
        starNftScopeRecord.setRemarks(userScopeAddDTO.getRemarks());
        starNftScopeRecord.setCreatedAt(new Date());
        starNftScopeRecordMapper.insertStarNftScopeRecord(starNftScopeRecord);
        int reSum;
        if (Objects.nonNull(result)) {
              result.setUserScope(result.getUserScope().add(userScopeAddDTO.getUserScope()));
             reSum= starNftUserScopeMapper.updateStarNftUserScopeVersion(result);
        } else {
            result=new StarNftUserScope();
            result.setId(IdUtil.getSnowflake().nextId());
            result.setUserScope(userScopeAddDTO.getUserScope());
            result.setUserId(userScopeAddDTO.getAccount());
            result.setScopeType(userScopeAddDTO.getScopeType());
            result.setVersion(0L);
            result.setModifiedAt(new Date());
             reSum= starNftUserScopeMapper.insertStarNftUserScope(result);
        }
        if (reSum<=0){
            throw new StarException("错误");
        }
        return  reSum;
    }

    /**
     * 修改用户积分
     *
     * @param starNftUserScope 用户积分
     * @return 结果
     */
    @Override
    public int updateStarNftUserScope(StarNftUserScope starNftUserScope) {
        return starNftUserScopeMapper.updateStarNftUserScope(starNftUserScope);
    }

    /**
     * 批量删除用户积分
     *
     * @param ids 需要删除的用户积分主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserScopeByIds(Long[] ids) {
        return starNftUserScopeMapper.deleteStarNftUserScopeByIds(ids);
    }

    /**
     * 删除用户积分信息
     *
     * @param id 用户积分主键
     * @return 结果
     */
    @Override
    public int deleteStarNftUserScopeById(Long id) {
        return starNftUserScopeMapper.deleteStarNftUserScopeById(id);
    }

    @Override
    public List<StarNftUserScope> selectScopeByAccount(Long id) {
        StarNftUserScope starNftUserScope = new StarNftUserScope();
        starNftUserScope.setUserId(id);
        return starNftUserScopeMapper.selectStarNftUserScopeList(starNftUserScope);
    }
}
