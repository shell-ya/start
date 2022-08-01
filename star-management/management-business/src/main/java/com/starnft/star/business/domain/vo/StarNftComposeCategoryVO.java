package com.starnft.star.business.domain.vo;

import com.starnft.star.common.annotation.Excel;
import lombok.Data;

import java.util.List;

@Data
public class StarNftComposeCategoryVO {
    /** $column.columnComment */
    private Long id;

    /** 合成id */
    @Excel(name = "合成id")
    private Long composeId;

    /** 合成材料 */
    @Excel(name = "合成材料")
    private List<ComposeMaterial> composeMaterial;

    /** 是否需要消耗积分 */
    @Excel(name = "是否需要消耗积分")
    private Integer isScore;

    /** 积分类型 */
    @Excel(name = "积分类型")
    private Long composeScopeType;

    /** 积分数量 */
    @Excel(name = "积分数量")
    private Long composeScopeNumber;
}
