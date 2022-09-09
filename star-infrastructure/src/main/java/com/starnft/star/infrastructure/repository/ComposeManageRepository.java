package com.starnft.star.infrastructure.repository;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.common.enums.NumberStatusEnum;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.common.utils.BeanColverUtil;
import com.starnft.star.domain.compose.model.dto.ComposeRecordDTO;
import com.starnft.star.domain.compose.model.dto.ComposeUserArticleNumberDTO;
import com.starnft.star.domain.compose.repository.IComposeManageRepository;
import com.starnft.star.domain.number.model.vo.NumberDingVO;
import com.starnft.star.infrastructure.entity.compose.StarNftComposeRecord;
import com.starnft.star.infrastructure.entity.number.StarNftThemeNumber;
import com.starnft.star.infrastructure.entity.user.StarNftUserTheme;
import com.starnft.star.infrastructure.mapper.compose.StarNftComposeRecordMapper;
import com.starnft.star.infrastructure.mapper.number.StarNftThemeNumberMapper;
import com.starnft.star.infrastructure.mapper.user.StarNftUserThemeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class ComposeManageRepository implements IComposeManageRepository {
    @Resource
    private StarNftUserThemeMapper starNftUserThemeMapper;
    @Resource
    private StarNftThemeNumberMapper starNftThemeNumberMapper;
    @Resource
    private StarNftComposeRecordMapper starNftComposeRecordMapper;
    @Override
    public List<ComposeUserArticleNumberDTO> queryUserArticleNumberInfoByNumberIds(Long userId, List<Long> sourceIds, UserNumberStatusEnum purchased) {

        return starNftUserThemeMapper.queryComposeUserArticleNumberInfoByNumberIds(userId, sourceIds, purchased);
    }

    @Override
    public Boolean composeModifyUserNumberStatus(Long userId, List<Long> userNumberIds, UserNumberStatusEnum purchased, UserNumberStatusEnum destroy, NumberStatusEnum currentStatusEnum, NumberStatusEnum statusEnum, Boolean isDeleted) {
        LambdaQueryWrapper<StarNftUserTheme> wrapper = new LambdaQueryWrapper<StarNftUserTheme>()
                .in(StarNftUserTheme::getSeriesThemeId, userNumberIds)
                .eq(StarNftUserTheme::getUserId, userId)
                .eq(StarNftUserTheme::getStatus, purchased.getCode());
        StarNftUserTheme starNftUserTheme = new StarNftUserTheme();
        starNftUserTheme.setStatus(destroy.getCode());
        Boolean updateResult = starNftUserThemeMapper.update(starNftUserTheme, wrapper) == userNumberIds.size();
        StarNftThemeNumber starNftThemeNumber = new StarNftThemeNumber();
        starNftThemeNumber.setOwnerBy("-1");
        starNftThemeNumber.setStatus(statusEnum.getCode());
        starNftThemeNumber.setIsDelete(isDeleted);
        LambdaQueryWrapper<StarNftThemeNumber> numberWrapper = new LambdaQueryWrapper<>();
        numberWrapper
                .in(StarNftThemeNumber::getId, userNumberIds)
                .eq(StarNftThemeNumber::getOwnerBy, userId)
                .eq(StarNftThemeNumber::getStatus, currentStatusEnum.getCode());
        Boolean numberResult = starNftThemeNumberMapper.update(starNftThemeNumber, numberWrapper) == userNumberIds.size();
        return numberResult && updateResult;
    }
    public  Boolean insertComposeRecord(ComposeRecordDTO composeRecord){
        StarNftComposeRecord starNftComposeRecord = new StarNftComposeRecord();
        starNftComposeRecord.setComposeId(composeRecord.getComposeId());
        starNftComposeRecord.setSource(JSONUtil.toJsonStr(composeRecord.getSource()));
        starNftComposeRecord.setCategoryId(composeRecord.getCategoryId());
        starNftComposeRecord.setIsScope(composeRecord.getIsScope());
        starNftComposeRecord.setPrizeMessage(JSONUtil.toJsonStr(composeRecord.getPrizeMessage()));
        starNftComposeRecord.setPrizeType(composeRecord.getPrizeType());
        starNftComposeRecord.setScopeNumber(composeRecord.getScopeNumber());
        starNftComposeRecord.setUserId(composeRecord.getUserId());
        starNftComposeRecord.setCreatedAt(new Date());
        starNftComposeRecord.setId(composeRecord.getId());
       return starNftComposeRecordMapper.insert(starNftComposeRecord)>=1;
    }

    @Override
    public List<ComposeUserArticleNumberDTO> queryUserArticleNumberInfoBySeriesNumberIds(Long userId, List<Long> sourceIds, UserNumberStatusEnum purchased) {
        return starNftUserThemeMapper.queryComposeUserArticleNumberInfoBySeriesNumberIds(userId, sourceIds, purchased);
    }

}
