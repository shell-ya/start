package com.starnft.star.business.domain.vo;

import com.starnft.star.common.annotation.Excel;
import lombok.Data;
import org.web3j.abi.datatypes.Int;

import java.util.List;

@Data
public class ErrorOrder {
    @Excel(name = "用户Id")
    private Long userId;
    @Excel(name = "通用优先购")
    private Integer commonYou;
    @Excel(name = "指定优先购")
    private Integer zhidingYou;
    @Excel(name = "最大购买数量")
    private Integer maxOrder;
    @Excel(name = "订单数量")
    private Integer orderNum;
    @Excel(name = "订单编号")
    private List<String> orderSnList;
}
