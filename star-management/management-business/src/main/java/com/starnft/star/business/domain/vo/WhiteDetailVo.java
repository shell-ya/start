package com.starnft.star.business.domain.vo;

import com.starnft.star.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhiteDetailVo {
    @Excel(name = "用户id")
    private Long userId;
    @Excel(name = "次数")
    private Integer surplusTime;
}
