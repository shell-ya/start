package com.starnft.star.business.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @Date 2022/8/7 2:42 PM
 * @Author ： shellya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomAirdrop {
    private Long seriesThemeInfoId;
    private Long themeNumber;
    private List<Serializable> redisNumberList;
}
