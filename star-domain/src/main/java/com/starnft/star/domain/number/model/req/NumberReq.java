package com.starnft.star.domain.number.model.req;

import com.starnft.star.common.page.RequestPage;
import com.starnft.star.domain.number.model.OrderByEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class NumberReq extends RequestPage {
    private OrderByEnum orderBy;
    private Boolean upOrDown;
    private Boolean isSell;
    private Long id;
}
