package com.starnft.star.domain.scope.model.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

//    private Long userId;

    private BigDecimal scope;

    private Integer mold;

    private String remarks;

    private Date createdAt;
}
