package com.starnft.star.application.process.props.invokers;

import com.starnft.star.application.process.props.invokers.base.IPropsStuffDelivery;
import com.starnft.star.application.process.props.invokers.loader.InvokersLoader;
import com.starnft.star.application.process.props.model.PropsTrigger;
import com.starnft.star.domain.prop.model.vo.PropsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class PropsExecutor implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

    @Resource
    private InvokersLoader invokersLoader;

    public void execute(Long uid, PropsVO prop) {
        String execution = invokersLoader.executionsMapping.get(prop.getId());
        IPropsStuffDelivery delivery = (IPropsStuffDelivery) applicationContext.getBean(execution);
        try {
            delivery.propsUsing(new PropsTrigger(uid, prop));
        } catch (Exception e) {
            log.error("道具使用失败！uid :[{}] prop:[{}]", uid, prop, e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
