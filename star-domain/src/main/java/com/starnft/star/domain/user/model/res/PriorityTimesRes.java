package com.starnft.star.domain.user.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriorityTimesRes {

    private String uid;

    private String goodsId;

    private int times;
}
