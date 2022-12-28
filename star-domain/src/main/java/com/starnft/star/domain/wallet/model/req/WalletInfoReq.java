package com.starnft.star.domain.wallet.model.req;

public class WalletInfoReq {

    /** 钱包id */
    private String walletId;

    private String thWId;
    /** uid */
    private Long uid;

    public String getThWId() {
        return thWId;
    }

    public void setThWId(String thWId) {
        this.thWId = thWId;
    }

    public WalletInfoReq(Long uid) {
        this.uid = uid;
    }

    public WalletInfoReq(String walletId, Long uid) {
        this.walletId = walletId;
        this.uid = uid;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
