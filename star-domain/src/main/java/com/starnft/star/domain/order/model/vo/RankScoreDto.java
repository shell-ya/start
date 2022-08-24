package com.starnft.star.domain.order.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2022/8/23 5:39 PM
 * @Author ： shellya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankScoreDto {
    private Long userId;
    private Integer score;
}
