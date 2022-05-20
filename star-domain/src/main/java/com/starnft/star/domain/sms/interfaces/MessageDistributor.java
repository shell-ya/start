package com.starnft.star.domain.sms.interfaces;

import java.util.Set;

public interface MessageDistributor {
    /**
     * 单人短信投放
     * @return
     */
     Boolean delivery(String mobile,String context);



     Integer deliveryBatch(Set<String> mobile, String context);

}
