package com.starnft.star.common.chain.model.req;

import lombok.Data;

@Data
public class TokenQueryReq {
    private String contractAddress; //	合约地址	字符串	是
    private Integer tokenId; //	token编号	整型	是
}
