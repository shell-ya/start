package com.starnft.star.common.utils.redis;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author WeiChunLAI
 */
public class RedisLuaUtils {
    private RedisLuaUtils(){throw  new UnsupportedOperationException("Unsupported Operation");}

    /**
     * 脚本所在的位置
     */
    private static final String SCRIPI_ROOT = "/redis_lua_script/";

    private static class RedisInstanceHolder {
        private static final RedisScript<Boolean> lock;

        static {
            DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
            defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(SCRIPI_ROOT +"lock.lua")));
            defaultRedisScript.setResultType(Boolean.class);
            lock = defaultRedisScript;
        }

        private static RedisScript<Boolean> lockInstance(){return lock;}
    }

    public static RedisScript<Boolean> lock() {return RedisInstanceHolder.lockInstance();}
}
