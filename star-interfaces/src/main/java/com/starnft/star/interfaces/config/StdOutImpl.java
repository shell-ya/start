package com.starnft.star.interfaces.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

/**
 * StdOutImpl
 * 日志文件输出Sql
 *
 * @date 2022/3/13
 * @see org.apache.ibatis.logging.stdout.StdOutImpl
 */
@Slf4j
public class StdOutImpl implements Log {

    public StdOutImpl(String clazz) {
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        System.err.println(s);
        log.error(s, e);
        e.printStackTrace(System.err);
    }

    @Override
    public void error(String s) {
        System.err.println(s);
        log.error(s);
    }

    @Override
    public void debug(String s) {
//        log.debug(s);
    }

    @Override
    public void trace(String s) {
//        log.trace(s);
    }

    @Override
    public void warn(String s) {
        System.out.println(s);
        log.warn(s);
    }
}