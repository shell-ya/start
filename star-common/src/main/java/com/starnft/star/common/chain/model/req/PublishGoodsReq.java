package com.starnft.star.common.chain.model.req;

import lombok.Data;

@Data
public class PublishGoodsReq {
    private String userId;
    private String userKey;
    private String name;      //	专题名称	字符串	是
    private Integer pieceCount;   //	NFR份数[1-10000]	整型	是
    private String[] productIds;  //	商家数字商品ID数组，可以是有意义的产品编号，每一个NFT一个	字符串数组	否
    private String author;     //	作者	字符串	否
    private String feature;      //作品特征、版权说明等，每一次铸造都会上链，内容不宜过多。	字符串	否
    private Integer baseTokenId; //自定义
    private String tokenId;      // 起始值，创建合约有效 (若请求参数指定了 contractAddress ，则 baseTokenId 需设置为已发行 tokenId 的最大值。)	整型	否
    private String initPrice;     //	发行价格	字符型	否
    private String contractAddress;    //合约地址参数（0xabc...），用于在某合约上分批铸造	字符型	否
    private String mintAddresses;     //	发行目标地址，铸造后，直接转移到该地址，和需要发行的数量匹配或者唯一一个，该参数为空时，所有Token发给调用用户，有且只有一个发给唯一用户地址，正常一一匹配	字符串数组	否
}
