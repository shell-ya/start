package com.starnft.star.common.chain.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class CreateAccountRes implements Serializable {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private DataDTO data;
    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("pubKey")
        private PubKeyDTO pubKey;
        @NoArgsConstructor
        @Data
        public static class PubKeyDTO {
            @JsonProperty("publicKey")
            private String publicKey;
            @JsonProperty("address")
            private String address;
        }
    }
}
