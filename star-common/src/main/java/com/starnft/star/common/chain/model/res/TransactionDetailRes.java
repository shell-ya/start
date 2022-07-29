package com.starnft.star.common.chain.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TransactionDetailRes {
  @JsonProperty("code")
  private Integer code;
  @JsonProperty("message")
  private String message;
  @JsonProperty("data")
  private DataDTO data;

  @NoArgsConstructor
  @Data
  public static class DataDTO {
    @JsonProperty("isSuccess")
    private Boolean isSuccess;
    @JsonProperty("output")
    private String output;
    @JsonProperty("tokenId")
    private String tokenId;
  }
}
