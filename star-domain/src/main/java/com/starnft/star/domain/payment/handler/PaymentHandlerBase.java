package com.starnft.star.domain.payment.handler;

import com.starnft.star.common.template.FreeMakerTemplateHelper;
import com.starnft.star.common.template.TemplateHelper;
import com.starnft.star.domain.payment.config.PaymentConfiguration;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.domain.support.process.ProcessInteractionHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

@Slf4j
public abstract class PaymentHandlerBase
        extends PaymentConfiguration implements IPaymentHandler, ApplicationContextAware, ProcessInteractionHolder {

    private ApplicationContext applicationContext;

    /**
     * @param paymentRich 是一个聚合对象 里面包含用户信息 订单信息等支付所需要的信息
     *                    在对接第三方支付时需要将用户唯一标识 作为扩展字段传入 在接收回调信息时候会获取到 原用户标识 以便于以后以用户标识作为分库分表路由的唯一标识
     * @return PaymentRes
     * @author Ryan Z / haoran
     * @description 支付
     * @date 2022/5/24
     */
    @Override
    public PaymentRes pay(PaymentRich paymentRich) {
        //验证参数边界 规则
        verifyLegality(paymentRich);
        //获取对应的厂商 渠道 配置信息
        Map<String, String> vendorConf = super.getVendorConf(getVendor(), getPayChannel());

        if (null == vendorConf) {
            throw new RuntimeException("配置信息未被加载，请检查配置！");
        }
        //执行支付流程
        return doPay(paymentRich, vendorConf);
    }

    protected String processTemplate(final String templateName, Object... data) {
        TemplateHelper templateHelper = applicationContext.getBean(FreeMakerTemplateHelper.class);
        String res = null;
        try {
            res = templateHelper.process(templateName, buildDataModel(data));
        } catch (Exception e) {
            log.error("[{}] 执行模版解析错误 templateName :{}", this.getClass().getName(), templateName, e);
        }
        return res;
    }

    protected abstract void verifyLegality(PaymentRich req);

    protected abstract PaymentRes doPay(PaymentRich paymentRich, Map<String, String> vendorConf);

    protected abstract Map<String, Object> buildDataModel(Object... data);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
