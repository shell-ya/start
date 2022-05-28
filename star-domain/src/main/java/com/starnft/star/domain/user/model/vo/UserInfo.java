package com.starnft.star.domain.user.model.vo;

import lombok.Data;


/**
 * @author LaIWeiChun
 */
@Data
public class UserInfo {

    private Long id;

    private Long account;

    private String phone;

    private String nickName;

    private String avatar;

    private String plyPassword;

    private String password;

    private Boolean isActive;

    private Integer realPersonFlag;

    private String blockchainAddress;

    private String briefIntroduction;
}
