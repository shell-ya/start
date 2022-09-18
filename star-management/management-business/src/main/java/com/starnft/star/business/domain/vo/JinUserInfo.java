package com.starnft.star.business.domain.vo;

import com.starnft.star.common.annotation.Excel;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class JinUserInfo {
    @Excel(name = "用户id")
    private String userId;
    @Excel(name = "用户昵称")
    private String nickName;
    @Excel(name = "用户姓名")
    private String userName;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "创世")
    private Integer chuang;
    @Excel(name = "金牛")
    private Integer jinniu;
    @Excel(name = "白羊")
    private Integer baiyang;
    @Excel(name = "空头")
    private AtomicInteger kongtou = new AtomicInteger(0);
    @Excel(name = "优先购")
    private AtomicInteger youxiangou =  new AtomicInteger(0);;
}
