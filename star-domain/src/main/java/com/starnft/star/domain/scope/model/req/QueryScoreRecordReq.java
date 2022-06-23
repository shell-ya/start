package com.starnft.star.domain.scope.model.req;

import com.starnft.star.common.page.RequestPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
public class QueryScoreRecordReq  extends RequestPage {
    private Long userId;
}
