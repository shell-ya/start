package com.starnft.star.domain.scope.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreRecordRes implements Serializable {

    private Long id;

//    private Long userId;

    private BigDecimal scope;

    private Integer mold;

    private String remarks;

    private Date createdAt;
}
