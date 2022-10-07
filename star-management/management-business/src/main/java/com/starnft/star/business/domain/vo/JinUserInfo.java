package com.starnft.star.business.domain.vo;

import com.starnft.star.common.annotation.Excel;
import io.swagger.models.auth.In;
import lombok.Data;
import org.web3j.abi.datatypes.Int;

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
    @Excel(name = "地球指挥官")
    private Integer pan;
//    @Excel(name = " UR持有者")
//    private Integer ur;
//    @Excel(name = " 星际探寻者")
//    private Integer xj;
}
