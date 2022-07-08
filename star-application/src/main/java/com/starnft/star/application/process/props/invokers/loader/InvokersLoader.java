package com.starnft.star.application.process.props.invokers.loader;

import com.starnft.star.domain.prop.model.vo.PropsVO;
import com.starnft.star.domain.prop.service.IPropsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InvokersLoader {

    public  Map<Long, String> executionsMapping = new ConcurrentHashMap<>();

    @Resource
    private IPropsService propsService;

    @PostConstruct
    private void initExec() {
        List<PropsVO> propsVOS = propsService.propsList();
        if (propsVOS.isEmpty()) {
            return;
        }
        for (PropsVO propsVO : propsVOS) {
            executionsMapping.put(propsVO.getId(), propsVO.getExecution());
        }
    }

//    @Scheduled(cron = "0/15 * * * * ?")
    private void refresh() {
        initExec();
    }
}
