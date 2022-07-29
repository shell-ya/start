package com.starnft.star.common.chain.model.req;

import lombok.Data;

@Data
public class GoodsTransferReq {
    private String userId;  //	用户ID	字符串	是
    private String userKey;  //	用户秘钥	字符串	是
    private String contractAddress; //	合约地址	字符串	是
    private String tokenId; //	token编号	整型	是
    private String from; //	发起方公钥地址	字符串	是
    private String to;    //接收方公钥地址	字符串	是
}
