package com.starnft.star.domain.compose.model.res;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class ComposePrizeRes implements Serializable {
    private Long id;
    /**
     * 合成id
     */
    private Long composeId;
    /**
     * 奖励类型
     */
    private Integer prizeType;
    /**
     * 奖励标记
     */
    private String prizeStamp;

    /**
     * 奖励几率
     */
    private BigDecimal prizeProbability;
    /**
     * 奖品数量
     */
    private BigDecimal prizeNumber;
    /**
     * 当前奖品数量
     */
    private BigDecimal currentPrizeNumber;
}
