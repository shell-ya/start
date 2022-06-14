package com.starnft.star.domain.wallet.util;


import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.wallet.model.vo.WalletConfigVO;
import com.starnft.star.domain.wallet.service.WalletConfig;
import com.starnft.star.domain.wallet.util.model.FeeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FeeCalculation {

    private static final Logger log = LoggerFactory.getLogger(FeeCalculation.class);

    public static FeeResult calculate(StarConstants.PayChannel channel, BigDecimal money) {
        WalletConfigVO config = WalletConfig.getConfig(channel);

        BigDecimal copyrightRate = config.getCopyrightRate();
        BigDecimal serviceRate = config.getServiceRate();

        //版权费
        BigDecimal copyrightFee = money.multiply(copyrightRate);
        //处理后的fee
        BigDecimal processedCopyrightFee = copyrightFee.setScale(2, RoundingMode.DOWN);
        //差值
        BigDecimal copyrightDValue = copyrightFee.subtract(processedCopyrightFee);

        //版权费
        BigDecimal serviceFee = money.multiply(serviceRate);
        //处理后的fee
        BigDecimal processedServiceFee = serviceFee.setScale(2, RoundingMode.DOWN);
        //差值
        BigDecimal serviceDValue = serviceFee.subtract(processedServiceFee);

        BigDecimal processed = BigDecimal.ONE
                .subtract(copyrightRate).subtract(serviceRate)
                .multiply(money)
                .add(copyrightDValue).add(serviceDValue)
                .setScale(2, RoundingMode.DOWN);

        if (log.isDebugEnabled()) {
            log.debug("版权税:{}  服务费:{} 最终费用:{}", processedCopyrightFee, processedServiceFee, processed);
        }

        return new FeeResult(processedCopyrightFee, processedCopyrightFee, processed);
    }

}
