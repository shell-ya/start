package com.starnft.star.domain.wallet.model.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Date 2022/8/1 11:21 AM
 * @Author ： shellya
 */
@Data
@ApiModel
public class CheckBatchReq implements Serializable {

    private Integer hours;
    private Integer size;
}
