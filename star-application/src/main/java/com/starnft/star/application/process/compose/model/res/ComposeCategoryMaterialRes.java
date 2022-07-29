package com.starnft.star.application.process.compose.model.res;

import cn.hutool.json.JSONUtil;
import com.starnft.star.domain.compose.model.dto.ComposeMaterialDTO;
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
public class ComposeCategoryMaterialRes implements  Serializable {

    private Long id;
    private List<ComposeMaterialDTO> composeMaterials;

    private Boolean isScore;

    private Integer composeScopeType;

    private Integer composeScopeNumber;

    public static void main(String[] args) {
        List<ComposeMaterialDTO> composeMaterials=new ArrayList<>();
        ComposeMaterialDTO composeMaterialDTO = new ComposeMaterialDTO();
        composeMaterialDTO.setNumber(3);
        composeMaterialDTO.setThemeId(999728276368560128L);
        composeMaterialDTO.setThemeImages("https://banner-1302318928.cos.ap-shanghai.myqcloud.com/theme/1658395373459_da5bcd1b.jpg");
        composeMaterialDTO.setThemeName("空投测试");
        composeMaterials.add(composeMaterialDTO);
        System.out.println(JSONUtil.toJsonStr(composeMaterials));

    }
}



