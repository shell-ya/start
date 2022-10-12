package com.starnft.star.business.domain.vo;

import com.starnft.star.common.annotation.Excel;
import lombok.Data;

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
    private Integer chaung;
    @Excel(name = "星际穿越者")
    private Integer chuangyue;
    @Excel(name = "盘星苍龙")
    private Integer pan;
    @Excel(name = "月球指挥官")
    private Integer yue;
//    @Excel(name = " UR持有者")
//    private Integer ur;
//    @Excel(name = " 星际探寻者")
//    private Integer xj;
}
