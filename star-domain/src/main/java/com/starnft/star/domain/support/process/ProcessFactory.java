package com.starnft.star.domain.support.process;

import com.starnft.star.common.constant.StarConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProcessFactory {

    @Resource(name = "restTemplateInteraction")
    IInteract jsonDataTransfer;

    //渠道调用方式缓存
    protected static final Map<StarConstants.ProcessType, IInteract> typeDataTransferMap = new ConcurrentHashMap<>(8);

    @PostConstruct
    public void init() {
        typeDataTransferMap.put(jsonDataTransfer.getSerSerializationType(), jsonDataTransfer);
    }

    public static IInteract getTypeDataTransferMap(StarConstants.ProcessType type) {
        return typeDataTransferMap.get(type);
    }

}
