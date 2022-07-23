package com.starnft.star.common.core.domain.sms;

import com.starnft.star.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class MobileModel implements Serializable {
    @Excel(name = "手机号")
    private String mobile;
}
