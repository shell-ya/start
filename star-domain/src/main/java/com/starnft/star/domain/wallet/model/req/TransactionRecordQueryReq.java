package com.starnft.star.domain.wallet.model.req;

import com.starnft.star.common.page.RequestPage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TransactionRecordQueryReq extends RequestPage implements Serializable {
    /** user id*/
    private Long userId;
    /** 开始时间*/
    private Date startDate;
    /** 结束时间*/
    private Date endDate;
    /** 交易类型*/
    private List<Integer> transactionType;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(List<Integer> transactionType) {
        this.transactionType = transactionType;
    }
}
