package com.starnft.star.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * @author WeiChunLAI
 */
public class LocalDateUtil {

    /**
     * 当前时间距第二天零点相隔的时间戳
     * @return
     */
    public static Long betweenTomorrowMillis() {
        return ChronoUnit.MILLIS.between(LocalDateTime.now(), tomorrow());
    }

    /**
     * 明天零时零分零秒时间
     *
     * @return
     */
    public static LocalDateTime tomorrow() {
        return LocalDateTime.now(ZoneOffset.ofHours(8)).withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(1);
    }
}
