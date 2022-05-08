package com.starnft.star.domain.wallet.model.req;

import java.io.Serializable;

public class WalletRecordReq implements Serializable {

    /** from userId*/
    private Long from_uid;
    /** to userId*/
    private Long to_uid;
    /** 流水号*/
    private String recordSn;



}
