package com.starnft.star.business.domain.vo;

import com.starnft.star.common.annotation.Excel;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class RongUserInfo {
    @Excel(name = "用户id")
    private String userId;
    @Excel(name = "用户昵称")
    private String nickName;
    @Excel(name = "用户姓名")
    private String userName;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "荣")
    private Integer rong;
    @Excel(name = "耀")
    private Integer yao;
    @Excel(name = "传")
    private Integer chuan;
    @Excel(name = "承")
    private Integer cheng;

}
