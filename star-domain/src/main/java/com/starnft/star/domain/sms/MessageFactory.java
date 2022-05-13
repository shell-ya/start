package com.starnft.star.domain.sms;

import cn.hutool.core.util.RandomUtil;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
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
     * @return
     */
    public Boolean pushCheckCodeMessage(SmsRequestDTO requestDTO) {
        String numbers = RandomUtil.randomNumbers(6);
        Boolean result = redisTemplate.opsForValue().setIfAbsent(requestDTO.getSmsType().getPrefix().concat(requestDTO.getPhone()), numbers, 5, TimeUnit.MINUTES);
        return messageAdapter.getDistributor(MessageTypeEnums.sw_message).pullCheckCode(requestDTO.getPhone(), numbers);
    }

    public Boolean verificationCheckCodeMessage(SmsVerifyCodePO po) {
        String code = (String) redisTemplate.opsForValue().get(po.getSmsType().getPrefix().concat(po.getPhone()));
        Optional.ofNullable(code).orElseThrow(() -> new StarException("验证码已过期"));
        log.info("缓存验证码{},传入验证码{},对比结果：{}", code.trim(), po.getCode(), code.equals(po.getCode()));
        if (!code.equals(po.getCode())) {
            throw new StarException(StarError.VERIFICATION_CODE_ERROR);
        }
        // 清除验证码
        redisTemplate.delete(po.getSmsType().getPrefix().concat(po.getPhone()));
        return true;
    }
}
