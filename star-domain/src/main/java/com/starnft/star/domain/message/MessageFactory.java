package com.starnft.star.domain.message;

import cn.hutool.core.util.RandomUtil;
import cn.lsnft.common.JsError;
import exception.ExceptionAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MessageFactory {
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    MessageAdapter messageAdapter;
    /**
     * 发送短信验证码
     *
     *
     * @return
     */
    public Boolean pushCheckCodeMessage(SmsRequestDTO requestDTO) {
        String numbers = RandomUtil.randomNumbers(6);
        Boolean result = redisTemplate.opsForValue().setIfAbsent(requestDTO.getSmsType().getPrefix().concat(requestDTO.getPhone()), numbers, 5, TimeUnit.MINUTES);
        return messageAdapter.getDistributor(MessageTypeEnums.sw_message).pullCheckCode(requestDTO.getPhone(), numbers);
    }

    public Boolean verificationCheckCodeMessage(SmsVerifyCodePO po) {
        String code = (String) redisTemplate.opsForValue().get(po.getSmsType().getPrefix().concat(po.getPhone()));
        ExceptionAssert.assertCondition(Objects.isNull(code), JsError.Default.CODE_MESSAGE_ERROR);
        log.info("缓存验证码{},传入验证码{},对比结果：{}", code.trim(), po.getCode(), code.equals(po.getCode()));
        ExceptionAssert.assertCondition(!code.equals(po.getCode()), JsError.Default.CODE_MESSAGE_ERROR);
        // 清除验证码
        redisTemplate.delete(po.getSmsType().getPrefix().concat(po.getPhone()));
        return true;
    }
}
