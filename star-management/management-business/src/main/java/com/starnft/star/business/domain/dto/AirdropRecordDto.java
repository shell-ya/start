package com.starnft.star.business.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @Date 2022/7/23 12:05 AM
 * @Author ： shellya
 */
@Data
public class AirdropRecordDto {

    private Long userId;
    private Integer airdropType;
    private List<RecordItem> recordItems;

}
