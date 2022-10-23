package com.starnft.star.interfaces.controller.trans;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.common.ResultCode;
import com.starnft.star.domain.notify.model.req.NotifyOrderReq;
import com.starnft.star.domain.notify.service.NotifyOrderService;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.interfaces.controller.trans.bo.C2CTransNotifyBO;
import com.starnft.star.interfaces.controller.trans.redis.RedisDistributedLock;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 新杉德支付回调服务
 */
@RestController
@RequestMapping("/newNotify")
@Api(tags = "回调服务｜NewNotifyController")
@Slf4j
public class NewNotifyController {

    @Autowired
    RedisDistributedLock redisDistributedLock;
    @Autowired
    IOrderProcessor orderProcessor;
    @Resource
    NotifyOrderService notifyOrderService;
    /**
     * 用户转账（C2C）-04010003 回调
     *
     * @return 用户转账（C2C）-04010003 回调
     */
    @TokenIgnore
    @ApiOperation("用户转账（C2C）-04010003 回调")
    @RequestMapping(path = "c2cTransNotify", method = {RequestMethod.GET, RequestMethod.POST})
    public String c2cTransNotify(HttpServletRequest req) {

        log.info("[c2cTransNotify]收到回调通知.....");

        // 1、获取参数
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (Objects.isNull(parameterMap) || parameterMap.isEmpty()) {
            log.error("parameterMap为空，跳过处理...");
            return "未获取到参数...";
        }

        C2CTransNotifyBO c2CTransNotifyBO = null;
        String data = req.getParameter("data");
        String sign = req.getParameter("sign");
        String signType = req.getParameter("signType");
        log.info("data====>{}", data);
        log.info("sign====>{}", sign);
        log.info("signType====>{}", signType);
        if (StrUtil.isBlank(data) || StrUtil.isBlank(sign) || StrUtil.isBlank(signType)) {
            log.error("回调参数为空....");
            return "回调参数为空....";
        }

        // 2、验签
        try {
            Class ceasClass = Class.forName("cn.com.sand.ceas.sdk.CeasHttpUtil");
            Object o = ceasClass.newInstance();
            Method method = ceasClass.getDeclaredMethod("verifySign", JSONObject.class);
            method.setAccessible(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", data);
            jsonObject.put("sign", sign);
            jsonObject.put("signType", signType);
            // 验证签名，执行verifySign方法
            boolean valid = (boolean) method.invoke(o, jsonObject);
            if (valid) {//验签成功
                log.info("verify sign success....");
                c2CTransNotifyBO = JSONUtil.toBean(data, C2CTransNotifyBO.class);
                log.info("接收到的异步通知数据为：{}", JSONUtil.toJsonStr(c2CTransNotifyBO));
            } else {//如果验签失败
                log.error("verify sign fail....");
                log.error("验签失败的签名字符串(data)为：{}", data);
                log.error("验签失败的签名值(sign)为：{}", sign);
            }
        } catch (Exception e) {
            log.error("系统错误：{}", e.getMessage(), e);
            return "系统错误";
        }

        if (Objects.isNull(c2CTransNotifyBO)) {
            log.error("验签之后BO对象为空，跳过处理...");
            return "验签失败....";
        }

        // 3、处理业务逻辑
        String lockKey = "lockKey_" + c2CTransNotifyBO.getSandSerialNo() + "_" + c2CTransNotifyBO.getOrderNo();
        String lockId = null;
        try {
            lockId = redisDistributedLock.lock(lockKey, 10, 10, TimeUnit.SECONDS);
            // 这里处理业务逻辑 todo
            //存储回调记录
            NotifyOrderReq orderReq = NotifyOrderReq.builder()
                    .orderSn(c2CTransNotifyBO.getOrderNo())
                    .payChannel("CloudAccount")
                    .createTime(new Date())
                    .message(c2CTransNotifyBO.getRespMsg())
                    .payTime(new Date())
                    .status(c2CTransNotifyBO.getOrderStatus().equals("00") ? ResultCode.SUCCESS.getCode():1)
                    .totalAmount(BigDecimal.valueOf(c2CTransNotifyBO.getAmount()))
                    .transSn(c2CTransNotifyBO.getSandSerialNo())
                    .uid(Long.parseLong(c2CTransNotifyBO.getPayeeInfo().getPayeeMemID()))
                    .build();
            notifyOrderService.saveOrder(orderReq);

            PayCheckRes payCheckRes = PayCheckRes
                    .builder()
                    .orderSn(c2CTransNotifyBO.getOrderNo())
                    .transSn(c2CTransNotifyBO.getSandSerialNo())
                    .uid(c2CTransNotifyBO.getPayeeInfo().getPayeeMemID())
                    .payChannel("CloudAccount")
                    .status(c2CTransNotifyBO.getOrderStatus().equals("00") ? ResultCode.SUCCESS.getCode():1)
                    .message(c2CTransNotifyBO.getRespMsg())
                    .totalAmount(BigDecimal.valueOf(c2CTransNotifyBO.getAmount()))
                    .build();


            orderProcessor.marketC2COrder(payCheckRes);

            return "respCode=000000";
        } catch (Exception e) {
            log.error("系统繁忙，请稍后操作：{}", e.getMessage(), e);
            return "系统繁忙，请稍后操作!";
        } finally {
            redisDistributedLock.releaseLock(lockKey, lockId);
        }
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        // 验证签名
        boolean valid;
        try {
            Class ceasClass = Class.forName("cn.com.sand.ceas.sdk.CeasHttpUtil");
            Object o = ceasClass.newInstance();
            Method method = ceasClass.getDeclaredMethod("verifySign", JSONObject.class);
            method.setAccessible(true);

            JSONObject jsonObject = new JSONObject();
            // ====================================修改下面三项进行测试==========================================
            String data = "{\"amount\":0.01,\"feeAmt\":0,\"mid\":\"6888806037286\",\"orderNo\":\"ccTest232297035739546\",\"orderStatus\":\"00\",\"payeeInfo\":{\"payeeAccName\":\"蔡晨\",\"payeeAccNo\":\"200009000031404\",\"payeeMemID\":\"778899\"},\"payerInfo\":{\"payerAccName\":\"蔡晨\",\"payerAccNo\":\"200009000091378\",\"payerMemID\":\"112233445566\"},\"postscript\":\"\",\"remark\":\"\",\"respCode\":\"00000\",\"respMsg\":\"成功\",\"respTime\":\"20221020211657\",\"sandSerialNo\":\"CEAS22102020540509300000224859\",\"transType\":\"C2C_TRANSFER\",\"userFeeAmt\":0}";
            String sign = "HlUvPd5mZjxrzybcihm8QBrmQeCvwpjJheuY4mJjZghGTJvgnr5ufZPQ+rf/kAiMKoDKZ9RhFdMw6+lmWiVJHW9eluIeO6ps98KkUpyOA1q9nDP1PccSST9POw49iEEkxBTlXjo0b9wrYvcdU0h9SK1a/TVc2slH26CYgYUGUwHMkcoSMF+YlS0+2/XqBV615jTkFNzBbS6neWsoYhB5pkHvFZVq0ppLeucCsQROT0wK6PlutciI/IA476AyB0BAPddM4XpiADe1vzC5oBHAsXWopHEwATVlNjkkVlFyuw6osx7FiE/0MsApFX+HZ4pA/kkn8366g1tVpf87CkU9/w==";
            String signType = "SHA1WithRSA";
            // ====================================修改上面三项进行测试==========================================

            jsonObject.put("data", data);
            jsonObject.put("sign", sign);
            jsonObject.put("signType", signType);
            //执行verifySign方法
            valid = (boolean) method.invoke(o, jsonObject);
            if (!valid) {//如果验签失败
                log.error("verify sign fail.");
                log.error("验签失败的签名字符串(data)为：" + data);
                log.error("验签失败的签名值(sign)为：" + sign);
            } else {//验签成功
                log.info("verify sign success");
                JSONObject dataJson = JSONObject.parseObject(data);
                if (dataJson != null) {
                    log.info("接收到的异步通知数据为：\n" + JSONObject.toJSONString(dataJson, true));
                } else {
                    log.error("接收异步通知数据异常！！！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }


}
