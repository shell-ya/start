package com.starnft.star.business.domain.vo;

import com.starnft.star.common.annotation.Excel;
import lombok.Data;

@Data
public class UserInfo {
    @Excel(name = "用户id")
    private String userId;
    @Excel(name = "用户姓名")
    private String userName;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "数量")
    private Integer nums;
}
