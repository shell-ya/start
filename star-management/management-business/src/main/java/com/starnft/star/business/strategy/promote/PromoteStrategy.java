package com.starnft.star.business.strategy.promote;


import java.util.List;

public interface PromoteStrategy<T> {
    public  Integer promoteOne(T promoteItem,String remark);
    public  String  promoteBatch(List<T> promoteItem,String remark);
}
