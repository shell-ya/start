package com.starnft.star.domain.user.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRealInfo implements Serializable {

    private Long account;

    private String phone;

    private String nickName;

    private String avatar;

    private String fullName;

    private String idNumber;

    private Integer realPersonFlag;
}
