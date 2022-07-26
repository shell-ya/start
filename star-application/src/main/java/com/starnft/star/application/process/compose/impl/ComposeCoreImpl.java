package com.starnft.star.application.process.compose.impl;

import cn.hutool.json.JSONUtil;
import com.starnft.star.application.process.article.IUserArticleCore;
import com.starnft.star.application.process.compose.IComposeCore;
import com.starnft.star.application.process.compose.model.req.UserMaterialReq;
import com.starnft.star.application.process.compose.model.res.ComposeCategoryMaterialRes;
import com.starnft.star.application.process.compose.model.res.ComposeDetailRes;
import com.starnft.star.common.enums.UserNumberStatusEnum;
import com.starnft.star.domain.article.model.vo.UserNumbersVO;
import com.starnft.star.domain.article.repository.IUserThemeRepository;
import com.starnft.star.domain.article.service.UserThemeService;
import com.starnft.star.domain.compose.model.dto.ComposeMaterialDTO;
import com.starnft.star.domain.compose.model.req.ComposeManageReq;
import com.starnft.star.domain.compose.model.res.ComposeCategoryRes;
import com.starnft.star.domain.compose.model.res.ComposeRes;
import com.starnft.star.domain.compose.service.IComposeService;
import com.starnft.star.domain.theme.service.ThemeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ComposeCoreImpl implements IComposeCore {

    @Resource
    private IComposeService composeService;
    @Resource
    private UserThemeService userThemeService;
    @Override
    public List<ComposeCategoryMaterialRes> composeMaterial(Long id) {
        //依据合成ID查询类目信息
        List<ComposeCategoryRes> categoryResArray = composeService.composeCategory(id);
        List<ComposeCategoryMaterialRes> collect = categoryResArray
                .stream()
                .map(item -> ComposeCategoryMaterialRes
                        .builder()
                        .composeScopeType(item.getComposeScopeType())
                        .composeScopeNumber(item.getComposeScopeNumber())
                        .id(item.getId())
                        .isScore(item.getIsScore())
                        .composeMaterials(JSONUtil.toList(item.getComposeMaterial(), ComposeMaterialDTO.class))
                        .build()
                ).collect(Collectors.toList());
        return collect;
    }

    @Override
    public ComposeDetailRes composeDetails(Long id) {
        ComposeRes composeRes = composeService.composeDetails(id);
        List<ComposeCategoryMaterialRes> composeCategoryMaterialRes = composeMaterial(id);
        ComposeDetailRes composeDetailRes = getComposeDetailRes(composeRes, composeCategoryMaterialRes);
        return composeDetailRes;
    }

    @Override
    public Map<Long, List<UserNumbersVO>> composeUserMaterial(UserMaterialReq userMaterialReq) {
        Long categoryId = userMaterialReq.getCategoryId();
        ComposeCategoryRes composeCategoryRes = composeService.composeCategoryByCategoryId(categoryId);
        List<ComposeMaterialDTO> composeMaterials = JSONUtil.toList(composeCategoryRes.getComposeMaterial(), ComposeMaterialDTO.class);
        List<Long> themeIds = composeMaterials.stream().map(ComposeMaterialDTO::getThemeId).collect(Collectors.toList());
        List<UserNumbersVO> userHaveNumbers = userThemeService.queryUserArticleNumberInfoByThemeIds(userMaterialReq.getUserId(), themeIds, UserNumberStatusEnum.PURCHASED);
        Map<Long, List<UserNumbersVO>> collect = userHaveNumbers.stream().collect(Collectors.groupingBy(UserNumbersVO::getThemeId));
        return collect;
    }

    @Override
    public Object composeManage(ComposeManageReq composeManageReq) {
        return null;
    }

    private ComposeDetailRes getComposeDetailRes(ComposeRes composeRes, List<ComposeCategoryMaterialRes> composeCategoryMaterialRes) {
        ComposeDetailRes composeDetailRes = new ComposeDetailRes();
        composeDetailRes.setComposeImages(composeRes.getComposeImages());
        composeDetailRes.setComposeName(composeRes.getComposeName());
        composeDetailRes.setComposeStatus(composeRes.getComposeStatus());
        composeDetailRes.setComposeRemark(composeRes.getComposeRemark());
        composeDetailRes.setEndAt(composeRes.getEndAt());
        composeDetailRes.setComposeCategoryMaterialResList(composeCategoryMaterialRes);
        composeDetailRes.setStartedAt(composeRes.getStartedAt());
        composeDetailRes.setId(composeRes.getId());
        return composeDetailRes;
    }
}
