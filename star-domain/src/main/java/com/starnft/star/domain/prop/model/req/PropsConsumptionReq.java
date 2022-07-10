package com.starnft.star.domain.prop.model.req;

import com.starnft.star.domain.prop.model.vo.PropsRelationVO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PropsConsumptionReq implements Serializable {

    private Long userId;

    private Long propId;

    private PropsRelationVO propsRelationVO;

    private Runnable invoker;
}
