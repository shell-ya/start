package com.starnft.star.domain.sms.strategy;


import com.starnft.star.domain.sms.interfaces.MessageStrategyInterface;
import org.springframework.stereotype.Component;

@Component("txMessageStrategy")
public class TxMessageStrategy implements MessageStrategyInterface {
    @Override
    public boolean checkCodeMessage(String mobile, String code) {

        return false;
    }
}
