package com.starnft.star.domain.payment.config;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.config.container.Channel;
import com.starnft.star.domain.payment.config.container.PayConf;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.ChannelConf;
import com.starnft.star.domain.support.process.config.TempConf;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentConfiguration extends PayConf {

    @Resource
    private ChannelConf channelConf;

    /**
     * @author Ryan Z / haoran
     * @description  获取对应厂商对应渠道配置
     *               若该厂商针对不同渠道有不同的配置 则需要传入渠道编码 反之传null
     * @date  2022/5/24
     * @param vendor 厂商
     * @param channel 渠道
     * @return properties
     */
    public Map<String, String> getVendorConf(StarConstants.Pay_Vendor vendor, StarConstants.PayChannel channel) {
        Map<String, String> props = null;
        for (Channel ch : getChannels()) {
            if (null != channel
                    && ch.getVendor().equals(vendor.name())
                    && channel.name().equals(ch.getChannelName())) {
                return ch.getProperties();
            }
            if (ch.getVendor().equals(vendor.name())) {
                props = ch.getProperties();
            }
        }
        return props;
    }


    /**
     * @author Ryan Z / haoran
     * @description 根据交易类型 获取对应信息
     * @date  2022/5/24
     * @param tradeType
     * @return TempConf
     */
    public TempConf getChannelConf(TradeType tradeType) {
        for (TempConf tempConf : channelConf.getTempConfs()) {
            if (tempConf.getTrade().equals(tradeType.name())) {
                return tempConf;
            }
        }
        return null;
    }


}
