package com.starnft.star.common.chain.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PublishGoodsRes {
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("contractAddress")
        private String contractAddress;
        @JsonProperty("deployTransactionHash")
        private String deployTransactionHash;
        @JsonProperty("products")
        private List<ProductsDTO> products;

        @NoArgsConstructor
        @Data
        public static class ProductsDTO {
            @JsonProperty("transactionHash")
            private String transactionHash;
            @JsonProperty("productId")
            private String productId;
            @JsonProperty("address")
            private String address;
            @JsonProperty("tokenId")
            private Integer tokenId;
            @JsonProperty("initPrice")
            private String initPrice;
        }
    }
}
