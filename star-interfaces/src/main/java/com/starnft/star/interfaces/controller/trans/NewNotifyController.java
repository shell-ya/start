package com.starnft.star.interfaces.controller.trans;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.starnft.star.application.process.order.IOrderProcessor;
import com.starnft.star.common.ResultCode;
import com.starnft.star.domain.notify.model.req.NotifyOrderReq;
import com.starnft.star.domain.notify.service.NotifyOrderService;
import com.starnft.star.domain.payment.model.res.PayCheckRes;
import com.starnft.star.interfaces.aop.BusinessTypeEnum;
import com.starnft.star.interfaces.aop.Log;
import com.starnft.star.interfaces.controller.trans.bo.C2BTransNotifyBO;
import com.starnft.star.interfaces.controller.trans.bo.C2CTransNotifyBO;
import com.starnft.star.interfaces.controller.trans.redis.RedisDistributedLock;
import com.starnft.star.interfaces.interceptor.TokenIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
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

        // 截取订单orderSn
        String orderNo = c2CTransNotifyBO.getOrderNo();
        orderNo = orderNo.substring(0, orderNo.indexOf("_"));

        String lockKey = "lockKey_" + c2CTransNotifyBO.getSandSerialNo() + "_" + orderNo;
        String lockId = null;
        try {
            lockId = redisDistributedLock.lock(lockKey, 10, 10, TimeUnit.SECONDS);
            // 这里处理业务逻辑 todo
            //存储回调记录
            NotifyOrderReq orderReq = NotifyOrderReq.builder()
                    .orderSn(orderNo)
                    .payChannel("CloudAccount")
                    .createTime(new Date())
                    .message(c2CTransNotifyBO.getRespMsg())
                    .payTime(new Date())
                    .status(c2CTransNotifyBO.getOrderStatus().equals("00") ? ResultCode.SUCCESS.getCode() : 1)
                    .totalAmount(BigDecimal.valueOf(c2CTransNotifyBO.getAmount()))
                    .transSn(c2CTransNotifyBO.getSandSerialNo())
                    .uid(Long.parseLong(c2CTransNotifyBO.getPayerInfo().getPayerMemID()))
                    .build();
            notifyOrderService.saveOrder(orderReq);

            PayCheckRes payCheckRes = PayCheckRes
                    .builder()
                    .orderSn(orderNo)
                    .transSn(c2CTransNotifyBO.getSandSerialNo())
                    .uid(c2CTransNotifyBO.getPayeeInfo().getPayeeMemID())
                    .payChannel("CloudAccount")
                    .status(c2CTransNotifyBO.getOrderStatus().equals("00") ? ResultCode.SUCCESS.getCode() : 1)
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
     * 用户消费（C2B）-04010001 回调
     *
     * @return 用户消费（C2B）-04010001 回调
     */
    @TokenIgnore
    @ApiOperation("用户消费（C2B）-04010001 回调")
    @RequestMapping(path = "c2bTransNotify", method = {RequestMethod.GET, RequestMethod.POST})
    public String c2bTransNotify(HttpServletRequest req) {

        log.info("[c2bTransNotify]收到回调通知.....");

        // 1、获取参数
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (Objects.isNull(parameterMap) || parameterMap.isEmpty()) {
            log.error("parameterMap为空，跳过处理...");
            return "未获取到参数...";
        }

        C2BTransNotifyBO c2bTransNotifyBO = null;
        String data = req.getParameter("data");
        String sign = req.getParameter("sign");

        log.info("data====>{}", data);
        log.info("sign====>{}", sign);

        if (StrUtil.isBlank(data) || StrUtil.isBlank(sign)) {
            log.error("回调参数为空....");
            return "回调参数为空....";
        }

        try {
            // 2、加载配置文件
            log.info("加载证书...");
            SDKConfig.getConfig().loadPropertiesFromSrc();
            //加载证书
            CertUtil.init(SDKConfig.getConfig().getSandCertPath(), SDKConfig.getConfig().getSignCertPath(), SDKConfig.getConfig().getSignCertPwd());
        } catch (Exception e) {
            log.error("加载证书异常，异常信息:{}", e.getMessage(), e);
        }

        // 3、验签
        try {
            boolean valid = CryptoUtil.verifyDigitalSign(data.getBytes("utf-8"), Base64.decodeBase64(sign), CertUtil.getPublicKey(), "SHA1WithRSA");
            if (!valid) {
                log.error("verify sign fail.");
                log.error("签名字符串(data)为：" + data);
                log.error("签名值(sign)为：" + sign);
            } else {
                log.info("verify sign success");
                c2bTransNotifyBO = JSONUtil.toBean(data, C2BTransNotifyBO.class);
            }
        } catch (Exception e) {
            log.error("验签错误：{}", e.getMessage(), e);
        }

        if (Objects.isNull(c2bTransNotifyBO)) {
            log.error("验签之后BO对象为空，跳过处理...");
            return "验签失败....";
        }

        // 4、处理业务逻辑

        // 截取订单orderSn
        String orderCode = c2bTransNotifyBO.getBody().getOrderCode();
        orderCode = orderCode.substring(0, orderCode.indexOf("_"));

        String lockKey = "lockKey_" + orderCode;
        String lockId = null;
        try {
            lockId = redisDistributedLock.lock(lockKey, 10, 10, TimeUnit.SECONDS);
            // 这里处理业务逻辑 todo
            //存储回调记录
            int i = Integer.parseInt(c2bTransNotifyBO.getBody().getSettleAmount());
            BigDecimal payAmount = BigDecimal.valueOf(i * 0.01);
            NotifyOrderReq orderReq = NotifyOrderReq.builder()
                    .orderSn(orderCode)
                    .payChannel("CloudAccount")
                    .createTime(new Date())
                    .message(c2bTransNotifyBO.getHead().getRespMsg())
                    .payTime(new Date())
                    .status(c2bTransNotifyBO.getHead().getRespCode().equals("000000") ? ResultCode.SUCCESS.getCode() : 1)
                    .totalAmount(payAmount)
                    .transSn(c2bTransNotifyBO.getBody().getPayOrderCode())
                    // .uid(Long.parseLong(c2CTransNotifyBO.getPayerInfo().getPayerMemID()))
                    .build();
            notifyOrderService.saveOrder(orderReq);

            PayCheckRes payCheckRes = PayCheckRes
                    .builder()
                    .orderSn(orderCode)
                    .transSn(c2bTransNotifyBO.getBody().getPayOrderCode())
                    // .uid(c2CTransNotifyBO.getPayeeInfo().getPayeeMemID())
                    .payChannel("CloudAccount")
                    .status(c2bTransNotifyBO.getHead().getRespCode().equals("000000") ? ResultCode.SUCCESS.getCode() : 1)
                    .message(c2bTransNotifyBO.getHead().getRespMsg())
                    .totalAmount(payAmount)
                    .sandSerialNo(c2bTransNotifyBO.getBody().getTradeNo())
                    .build();

            orderProcessor.marketC2BOrder(payCheckRes);

            return "respCode=000000";
        } catch (Exception e) {
            log.error("系统繁忙，请稍后操作：{}", e.getMessage(), e);
            return "系统繁忙，请稍后操作!";
        } finally {
            redisDistributedLock.releaseLock(lockKey, lockId);
        }
    }

    /**
     * 新收银台支付-06030003 回调
     *
     * @return 新收银台支付-06030003 回调
     */
    @TokenIgnore
    @ApiOperation("新收银台支付-06030003 回调")
    @RequestMapping(path = "sandCashierPayNotify", method = {RequestMethod.GET, RequestMethod.POST})
    @Log(title = "市场订单新收银台支付回调", businessType = BusinessTypeEnum.OTHER)
    public String sandCashierPayNotify(HttpServletRequest req) {

        log.info("[sandCashierPayNotify]收到回调通知.....");

        // 1、获取参数
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (Objects.isNull(parameterMap) || parameterMap.isEmpty()) {
            log.error("parameterMap为空，跳过处理...");
            return "未获取到参数...";
        }

        C2BTransNotifyBO c2bTransNotifyBO = null;
        String data = req.getParameter("data");
        String sign = req.getParameter("sign");

        log.info("data====>{}", data);
        log.info("sign====>{}", sign);

        if (StrUtil.isBlank(data) || StrUtil.isBlank(sign)) {
            log.error("回调参数为空....");
            return "回调参数为空....";
        }

        try {
            // 2、加载配置文件
            log.info("加载证书...");
            SDKConfig.getConfig().loadPropertiesFromSrc();
            //加载证书
            CertUtil.init(SDKConfig.getConfig().getSandCertPath(), SDKConfig.getConfig().getSignCertPath(), SDKConfig.getConfig().getSignCertPwd());
        } catch (Exception e) {
            log.error("加载证书异常，异常信息:{}", e.getMessage(), e);
        }

        // 3、验签
        try {
            boolean valid = CryptoUtil.verifyDigitalSign(data.getBytes("utf-8"), Base64.decodeBase64(sign), CertUtil.getPublicKey(), "SHA1WithRSA");
            if (!valid) {
                log.error("verify sign fail.");
                log.error("签名字符串(data)为：" + data);
                log.error("签名值(sign)为：" + sign);
            } else {
                log.info("verify sign success");
                // c2bTransNotifyBO = JSONUtil.toBean(data, C2BTransNotifyBO.class);
            }
        } catch (Exception e) {
            log.error("验签错误：{}", e.getMessage(), e);
        }

        // if (Objects.isNull(c2bTransNotifyBO)) {
        //     log.error("验签之后BO对象为空，跳过处理...");
        //     return "验签失败....";
        // }

        // 4、处理业务逻辑

        // 截取订单orderSn
        // String orderCode = c2bTransNotifyBO.getBody().getOrderCode();
        // orderCode = orderCode.substring(0, orderCode.indexOf("_"));
        //
        // String lockKey = "lockKey_" + orderCode;
        // String lockId = null;
        // try {
        //     lockId = redisDistributedLock.lock(lockKey, 10, 10, TimeUnit.SECONDS);
        //     // 这里处理业务逻辑 todo
        //     //存储回调记录
        //     int i = Integer.parseInt(c2bTransNotifyBO.getBody().getSettleAmount());
        //     BigDecimal payAmount = BigDecimal.valueOf(i * 0.01);
        //     NotifyOrderReq orderReq = NotifyOrderReq.builder()
        //             .orderSn(orderCode)
        //             .payChannel("CloudAccount")
        //             .createTime(new Date())
        //             .message(c2bTransNotifyBO.getHead().getRespMsg())
        //             .payTime(new Date())
        //             .status(c2bTransNotifyBO.getHead().getRespCode().equals("000000") ? ResultCode.SUCCESS.getCode() : 1)
        //             .totalAmount(payAmount)
        //             .transSn(c2bTransNotifyBO.getBody().getPayOrderCode())
        //             // .uid(Long.parseLong(c2CTransNotifyBO.getPayerInfo().getPayerMemID()))
        //             .build();
        //     notifyOrderService.saveOrder(orderReq);
        //
        //     PayCheckRes payCheckRes = PayCheckRes
        //             .builder()
        //             .orderSn(orderCode)
        //             .transSn(c2bTransNotifyBO.getBody().getPayOrderCode())
        //             // .uid(c2CTransNotifyBO.getPayeeInfo().getPayeeMemID())
        //             .payChannel("CloudAccount")
        //             .status(c2bTransNotifyBO.getHead().getRespCode().equals("000000") ? ResultCode.SUCCESS.getCode() : 1)
        //             .message(c2bTransNotifyBO.getHead().getRespMsg())
        //             .totalAmount(payAmount)
        //             .sandSerialNo(c2bTransNotifyBO.getBody().getTradeNo())
        //             .build();
        //
        //     orderProcessor.marketC2BOrder(payCheckRes);
        //
        //     return "respCode=000000";
        // } catch (Exception e) {
        //     log.error("系统繁忙，请稍后操作：{}", e.getMessage(), e);
        //     return "系统繁忙，请稍后操作!";
        // } finally {
        //     redisDistributedLock.releaseLock(lockKey, lockId);
        // }


        return "respCode=000000";
    }

    public static void C2B() {

        log.info("加载证书...");
        // 加载证书
        try {
            //加载配置文件
            SDKConfig.getConfig().loadPropertiesFromSrc();
            //加载证书
            CertUtil.init(SDKConfig.getConfig().getSandCertPath(), SDKConfig.getConfig().getSignCertPath(), SDKConfig.getConfig().getSignCertPwd());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String data = "{\"head\":{\"version\":\"1.0\",\"respTime\":\"20221115163027\",\"respCode\":\"000000\",\"respMsg\":\"成功\"},\"body\":{\"mid\":\"6888806044846\",\"orderCode\":\"1b2ymykgfzjcckd9a2fi\",\"tradeNo\":\"1b2ymykgfzjcckd9a2fi\",\"clearDate\":\"20221115\",\"totalAmount\":\"000000000014\",\"orderStatus\":\"1\",\"payTime\":\"20221115163026\",\"settleAmount\":\"000000000014\",\"buyerPayAmount\":\"000000000000\",\"discAmount\":\"000000000000\",\"txnCompleteTime\":\"\",\"payOrderCode\":\"20221115sdb56710000006271\",\"accLogonNo\":\"\",\"accNo\":\"\",\"midFee\":\"000000000000\",\"extraFee\":\"000000000000\",\"specialFee\":\"000000000000\",\"plMidFee\":\"000000000000\",\"bankserial\":\"\",\"externalProductCode\":\"\",\"cardNo\":\"\",\"creditFlag\":\"\",\"bid\":\"\",\"benefitAmount\":\"000000000000\",\"remittanceCode\":\"\",\"extend\":\"\",\"accountAmt\":\"000000000014\",\"masterAccount\":\"200229000020004\"}}";
        String sign = "UTXP0H1dOFNS9cEU7Mh3oEggRP1sBMUKtHORkufVVk/iWMz5Pa7rTwB4FvEbQigLwekWa+CcCXlLFQmUWN96TvE2HDuTo+PsIH4ZdLyAoSWVjIoVRXZIc6ycnE+NyhmbSnhzZgI+zl+pP4ZzklROv/7TCOj0MibuC+onCDk3fWJyTB36K5MdUTcWgsLyepd65amo3ywneV+AX8faIxgXx3jieAeXyppWRMNypemsP4QeG2lwTUgcWqVIHoFlpzWE6w6LoIa/gMtAehQonhCLnyN1D+qfBiZz3O9CKiJiqn3Flei7LdSJh4IityFCB+U9jRabwbgdxQHmR13hatyFtA==";
        // 验证签名
        boolean valid;
        try {

            valid = CryptoUtil.verifyDigitalSign(data.getBytes("utf-8"), Base64.decodeBase64(sign),
                    CertUtil.getPublicKey(), "SHA1WithRSA");
            if (!valid) {
                log.error("verify sign fail.");
                log.error("签名字符串(data)为：" + data);
                log.error("签名值(sign)为：" + sign);
            } else {
                log.info("verify sign success");
                JSONObject dataJson = JSONObject.parseObject(data);
                if (dataJson != null) {
                    log.info("通知业务数据为：" + JSONObject.toJSONString(dataJson, true));
                } else {
                    log.error("通知数据异常！！！");
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {

        String order = "TS123123_1";
        String substring = order.substring(0, order.indexOf("_"));
        System.out.println(substring);

        // C2B();
        //
        // C2C();
    }

    public static void C2C() {
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
