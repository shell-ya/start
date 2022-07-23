package com.starnft.star.business.strategy.promote;

import cn.hutool.core.util.IdUtil;
import com.starnft.star.business.support.sms.SwSmsSender;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("smsPromoteStrategy")
public class SmsPromoteStrategy implements  PromoteStrategy<String>{
    @Resource
    SwSmsSender swSmsSender;
    @Override
    public Integer promoteOne(String mobile,String remark) {
        return null;
    }

    @Override
    public String  promoteBatch(List<String> mobiles, String remark) {
        String orderSn =  IdUtil.getSnowflake().nextIdStr();
        String result = mobiles.stream().map(String::valueOf).collect(Collectors.joining(","));
        Assert.isTrue(swSmsSender.send(result, remark,orderSn),()->new StarException("提交发送失败"));
      return  orderSn;
    }
}
