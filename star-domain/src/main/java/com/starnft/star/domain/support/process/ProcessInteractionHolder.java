package com.starnft.star.domain.support.process;

import com.starnft.star.common.constant.StarConstants;

public interface ProcessInteractionHolder {
    /**
     * @param type
     * @return [ProcessInteraction]
     * @author Ryan Z / haoran
     * @description //获取对应的协议调用类
     * @date 2022/3/21
     */
    default IInteract obtainProcessInteraction(StarConstants.ProcessType type) {
        return ProcessFactory.getTypeDataTransferMap(type);
    }
}
