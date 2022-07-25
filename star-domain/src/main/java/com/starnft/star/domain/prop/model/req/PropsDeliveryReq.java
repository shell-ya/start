package com.starnft.star.domain.prop.model.req;

import com.starnft.star.domain.prop.model.vo.PropsVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PropsDeliveryReq {

    private Long uid;
    private PropsVO propsVO;
    private Integer propNums;
}
