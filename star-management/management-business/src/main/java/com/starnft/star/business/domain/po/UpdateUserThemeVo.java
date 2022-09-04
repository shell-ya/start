package com.starnft.star.business.domain.po;

import lombok.Data;

/**
 * @Date 2022/9/4 11:06 AM
 * @Author ： shellya
 */
@Data
public class UpdateUserThemeVo {
    private Long userId;
    private Long seriesThemeId;
    private Integer isDelete;
    private Integer status;
    private Integer beforeStatus;
}
