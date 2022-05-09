package com.starnft.star.domain.theme.model.req;

import com.starnft.star.common.page.RequestPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
//@NoArgsConstructor
public class ThemeReq  extends RequestPage {
    private Boolean isRecommend;
}
