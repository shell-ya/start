package com.starnft.star.domain.payment.router;

import com.google.common.collect.Lists;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.handler.IPaymentHandler;
import com.starnft.star.domain.support.key.model.DictionaryVO;
import com.starnft.star.domain.support.key.repo.IDictionaryRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PaymentRouter implements IPaymentRouter, ApplicationContextAware {

    @Resource
    private IDictionaryRepository dictionaryRepository;

    private ApplicationContext applicationContext;

    private final Map<StarConstants.PayChannel, IPaymentHandler> handlerMaps = new ConcurrentHashMap<>(8);

    @PostConstruct
    private void init() {
        Map<String, IPaymentHandler> handlers = applicationContext.getBeansOfType(IPaymentHandler.class);
        for (IPaymentHandler handler : handlers.values()) {
            Class<? extends IPaymentHandler> handlerClass = handler.getClass();
            if (!Modifier.isAbstract(handlerClass.getModifiers())) {
                handlerMaps.put(handler.getPayChannel(), handler);
            }
        }
    }


    @Override
    public List<Enum<StarConstants.PayChannel>> obtainSupported() {
        return Lists.newArrayList(StarConstants.PayChannel.values());
    }


    /**
     * @author Ryan Z / haoran
     * @description 获取支付具体执行器 该方法会先查找当前配置的支付厂商
     *              根据厂商再寻找对应厂商下的渠道支付 若该厂商下渠道找不到
     *              则随机路由一个其他厂商下拥有该渠道的执行器执行
     * @date  2022/5/24
     * @param payChannel
     * @return IPaymentHandler
     */
    @Override
    public IPaymentHandler payRoute(String payChannel) {

        List<DictionaryVO> dictionaries = dictionaryRepository.obtainDictionary(StarConstants.Pay_Vendor.class.getSimpleName());
        if (dictionaries.size() != 1) {
            throw new RuntimeException("厂商配置出错,启用厂商有多个或未启用任何厂商配置");
        }
        DictionaryVO supportVendor = dictionaries.get(0);

        // 如果在执行器中找到对应渠道对应厂商的 则返回对应的执行器 若未匹配 则选择一个任意厂商拥有对应渠道的执行器去执行
        IPaymentHandler handler = null;
        for (IPaymentHandler paymentHandler : handlerMaps.values()) {

            if (paymentHandler.getPayChannel().name().equals(payChannel)) {
                handler = paymentHandler;
            }
            //过滤当前渠道下 对应配置厂商的执行器
            if (paymentHandler.getPayChannel().name().equals(payChannel)
                    && supportVendor.getDictCode().equals(paymentHandler.getVendor().name())) {
                return paymentHandler;
            }
        }
        return handler;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
