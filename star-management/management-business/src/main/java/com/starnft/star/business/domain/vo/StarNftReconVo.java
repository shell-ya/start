package com.starnft.star.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Date 2022/7/15 5:04 PM
 * @Author ： shellya
 */
@Data
public class StarNftReconVo {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date createdAt;
    private Long createdBy;
    private String recordSn;
    private Long fromUid;
    private Long toUid;
    private Long tsType;
    private BigDecimal tsMoney;
    private String payChannel;
    private String payStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date payTime;
    /** 变动金额 增+ 减- */
    private BigDecimal balanceOffset;
    private BigDecimal currentBalance;
}
