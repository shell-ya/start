package com.starnft.star.domain.publisher.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherReq implements Serializable {
  private Long  publisherId;
}
