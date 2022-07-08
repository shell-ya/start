package com.starnft.star.application.process.props.invokers.base;

import com.starnft.star.application.process.props.model.PropsTrigger;

public interface IPropsStuffDelivery {

    /**
     * 执行道具效果
     *
     * @param propsTrigger
     * @throws Exception
     */
    void propsUsing(PropsTrigger propsTrigger) throws Exception;

}
