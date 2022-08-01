package com.starnft.star.application.process.compose.model.res;

import cn.hutool.json.JSONUtil;
import com.starnft.star.domain.compose.model.dto.ComposeMaterialDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("合成素材")
public class ComposeCategoryMaterialRes implements  Serializable {
    @ApiModelProperty("素材类目ID")
    private Long id;
    @ApiModelProperty("素材列表")
    private List<ComposeMaterialDTO> composeMaterials;
    @ApiModelProperty("是否需要积分")
    private Boolean isScore;
    @ApiModelProperty("积分类型")
    private Integer composeScopeType;
    @ApiModelProperty("积分数量")
    private Integer composeScopeNumber;

//    public static void main(String[] args) {
//        List<ComposeMaterialDTO> composeMaterials=new ArrayList<>();
//        ComposeMaterialDTO composeMaterialDTO = new ComposeMaterialDTO();
//        composeMaterialDTO.setNumber(3);
//        composeMaterialDTO.setThemeId(999728276368560128L);
//        composeMaterialDTO.setThemeImages("https://banner-1302318928.cos.ap-shanghai.myqcloud.com/theme/1658395373459_da5bcd1b.jpg");
//        composeMaterialDTO.setThemeName("空投测试");
//        composeMaterials.add(composeMaterialDTO);
//        System.out.println(JSONUtil.toJsonStr(composeMaterials));
//
//    }
}



