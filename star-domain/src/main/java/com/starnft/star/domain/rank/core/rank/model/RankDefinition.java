package com.starnft.star.domain.rank.core.rank.model;

import lombok.Data;

import java.util.Date;
@Data
public class RankDefinition {
    private Long rankType;
    private Date startTime;
    private Date endTime;
    private Integer isExtend;
    private String rankName;
    private String rankRemark;
    private Integer isTime;

}
