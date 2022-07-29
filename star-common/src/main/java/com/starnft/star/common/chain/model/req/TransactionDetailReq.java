package com.starnft.star.common.chain.model.req;

import com.starnft.star.common.chain.enums.MethodEnums;
import lombok.Data;

@Data
public class TransactionDetailReq {
    private MethodEnums methodName;
    private String  userId;
    private String  userKey;
    private String  transactionHash;
}
