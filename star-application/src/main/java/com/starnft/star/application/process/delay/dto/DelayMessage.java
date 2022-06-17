package com.starnft.star.application.process.delay.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Date 2022/6/17 5:40 PM
 * @Author ： shellya
 */
@Data
public class DelayMessage<T> implements Serializable {

    private String DelayConsumerType;

    private T messageBody;

}
