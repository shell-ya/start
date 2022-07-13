package com.starnft.star.common.utils;

public  class IsPhoneExists {
    //低位部分的偏移量
    private static final int LOW_OFFSET = 23;
    //用于跟手机号求与算出offset
    private static final long LOWWER_MOD = (1 << LOW_OFFSET) - 1;
    //与手机号求与算出高位部分(不过最后还是要右移,这一步可没有)
    private static final long PREFIX_MOD = ~LOWWER_MOD;
    //计算分区key
    public static String getKey(long phone) {
        return String.format("phone_bitmap_%d",(phone & PREFIX_MOD) >>> LOW_OFFSET);
    }
    //计算偏移量
    public static int getOffset(long phone) {
        return (int) (phone & LOWWER_MOD);
    }
}