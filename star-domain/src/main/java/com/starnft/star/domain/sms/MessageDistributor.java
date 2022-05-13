package com.starnft.star.domain.sms;

public interface MessageDistributor {
    /**
     * 单人短信投放
     * @return
     */
     Boolean delivery(String mobile,String context);



     Integer deliveryBatch(String mobile,String context);

}
