package com.starnft.star.business.support.rank.model;

import lombok.Data;

import java.util.Date;
@Data
public class RankDefinition {
    private Long rankType;
    private Date startTime;
    private Date endTime;
    private Integer isExtend;
    private String rankName;
    private Integer refreshTime;
    private Integer isTime;

}
