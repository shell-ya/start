package com.starnft.star.common.chain.model.req;

import lombok.Data;

@Data
public class UserUpdateKeyReq {

  private String   userId;//	用户ID	字符串	是
    private String  userKey;//	用户秘钥	字符串	是
    private String newUserKey;//	用户新密钥	字符串	是
}
