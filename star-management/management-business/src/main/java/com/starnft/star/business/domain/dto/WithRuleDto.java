package com.starnft.star.business.domain.dto;

import com.starnft.star.common.annotation.Excel;
import lombok.Data;
import org.web3j.abi.datatypes.Int;

/**
 * @Date 2022/8/6 5:02 PM
 * @Author ： shellya
 */
@Data
public class WithRuleDto {
    @Excel(name = "用户id")
    private Long userId;
    @Excel(name = "白名单次数")
    private Integer withNum = 1;
}
