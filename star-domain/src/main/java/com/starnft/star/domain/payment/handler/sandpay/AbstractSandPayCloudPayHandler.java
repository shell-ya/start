package com.starnft.star.domain.payment.handler.sandpay;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.payment.handler.ISandPayCloudPayHandler;
import com.starnft.star.domain.payment.helper.*;
import com.starnft.star.domain.payment.model.req.CloudAccountBalanceReq;
import com.starnft.star.domain.payment.model.req.CloudAccountOPenReq;
import com.starnft.star.domain.payment.model.req.CloudAccountStatusReq;
import com.starnft.star.domain.payment.model.res.CloudAccountBalanceRes;
import com.starnft.star.domain.payment.model.res.CloudAccountOPenRes;
import com.starnft.star.domain.payment.model.res.CloudAccountStatusRes;

import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.TempConf;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.checkerframework.checker.nullness.qual.Nullable;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class AbstractSandPayCloudPayHandler extends AbstractSandPayHandler implements ISandPayCloudPayHandler {

    @SneakyThrows
    @Override
    public CloudAccountStatusRes accountStatus(CloudAccountStatusReq cloudAccountStatusReq) {
        Map<String, String> vendor = super.getVendorConf(getVendor(), getPayChannel());
        TempConf channelConf = getChannelConf(TradeType.Cloud_Account_Status_SandPay);
        Map<String, Object> req = new HashMap<>();
        String orderSn = IdUtil.getSnowflake().nextIdStr();
        req.put("bizUserNo", cloudAccountStatusReq.getUserId());
        req.put("customerOrderNo", orderSn);
        log.info("加密前的参数：「{}」", req);
        JSONObject object = JSONUtil.parseObj(req);
        JSONObject encrypt = encrypt(JSONUtil.toJsonStr(object));
        encrypt.set("mid", vendor.get("mid"));
        encrypt.set("customerOrderNo", orderSn);
        encrypt.set("timestamp", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        encrypt.set("version", "1.0.0");
        sign(encrypt);
        log.info("请求报文：{}", JSONUtil.toJsonStr(encrypt));
        String post = HttpUtil.post(channelConf.getHttpConf().getApiUrl(), JSONUtil.toJsonStr(encrypt));
        log.info("解密前「{}」", post);
        if (!verifySign(post)) {
            throw new StarException("验证签名错误");
        }
        JSONObject decrypt = decrypt(post);
        log.info("解密后「{}」", decrypt);
        CloudAccountStatusRes openPayStatusAccountVO = new CloudAccountStatusRes();
        if (decrypt.getStr("responseCode").equals("05017")) {
            openPayStatusAccountVO.setStatus(false);
            return openPayStatusAccountVO;
        }
        if (decrypt.getStr("responseCode").equals("00000")) {
            String responseStatus = decrypt.getStr("responseStatus");
            if (Objects.nonNull(responseStatus)) {
                openPayStatusAccountVO.setStatus(true);
                return openPayStatusAccountVO;
            } else {
                openPayStatusAccountVO.setStatus(false);
                return openPayStatusAccountVO;
            }
        }
        openPayStatusAccountVO.setStatus(false);
        return openPayStatusAccountVO;
    }

    @SneakyThrows
    @Override
    public CloudAccountBalanceRes queryBalance(CloudAccountBalanceReq cloudAccountBalanceReq) {
        Map<String, String> vendor = super.getVendorConf(getVendor(), getPayChannel());
        TempConf channelConf = getChannelConf(TradeType.Cloud_Account_Balance_SandPay);
        HashMap<@Nullable String, @Nullable Object> temp = Maps.newHashMap();
        String s = IdUtil.getSnowflake().nextIdStr();
        temp.put("accountType", "01");
        temp.put("mid", vendor.get("mid"));
        temp.put("bizUserNo", cloudAccountBalanceReq.getUserId());
        temp.put("customerOrderNo", s);
        String render = JSONUtil.toJsonStr(temp);
        JSONObject encrypt = encrypt(render);
        sign(encrypt);
        encrypt.set("mid", vendor.get("mid"));
        encrypt.set("customerOrderNo", s);
        log.info("请求报文：{}", JSONUtil.toJsonStr(encrypt));
        String post = HttpUtil.post(channelConf.getHttpConf().getApiUrl(), JSONUtil.toJsonStr(encrypt));
        log.info("解密前「{}」", post);
        if (!verifySign(post)) {
            throw new StarException("验证签名错误");
        }
        JSONObject decrypt = decrypt(post);
        log.info("解密后「{}」", decrypt);
        CloudAccountBalanceRes userBalanceVO = new CloudAccountBalanceRes();
        if (decrypt.getStr("responseCode").equals("00000")) {
            String responseStatus = decrypt.getStr("responseStatus");
            if (Objects.nonNull(responseStatus)) {
                userBalanceVO.setResponseStatus(responseStatus);
                JSONArray accountList = decrypt.getJSONArray("accountList");
                JSONObject jsonObject = accountList.getJSONObject(0);
                BigDecimal balance = jsonObject.getBigDecimal("availableBal");
                userBalanceVO.setBalance(balance);
                return userBalanceVO;
            }
        }
        throw new StarException("账户未开通");
    }
    @Override
    public CloudAccountOPenRes openAccount(CloudAccountOPenReq cloudAccountOPenReq) {
        Assert.notBlank(cloudAccountOPenReq.getUserId(), () -> {
            throw new StarException("请实名认证");
        });
        Assert.notBlank(cloudAccountOPenReq.getIdCard(), () -> {
            throw new StarException("请实名认证");
        });
        Assert.notBlank(cloudAccountOPenReq.getRealName(), () -> {
            throw new StarException("请实名认证");
        });
        Map<String, String> vendor = super.getVendorConf(getVendor(), getPayChannel());
        TempConf channelConf = getChannelConf(TradeType.Cloud_Account_Open_SandPay);
        String startTime = TemplateHelper.getInstance().getStartTime();
        String endTime = TemplateHelper.getInstance().getEndTime();
        String orderSn = IdUtil.getSnowflake().nextIdStr();
        String result = super.processTemplate(channelConf.getSignTempPath(), vendor, cloudAccountOPenReq, startTime, endTime, orderSn);
        result = StrUtil.removeAllLineBreaks(result);
        String sign = TemplateHelper.getInstance().encode(result).toUpperCase();
        String res = super.processTemplate(channelConf.getResTempPath(), vendor, cloudAccountOPenReq, startTime, endTime, orderSn,sign);
        res= StrUtil.removeAllLineBreaks(res);
        CloudAccountOPenRes cloudAccountOPenRes = new CloudAccountOPenRes();
        cloudAccountOPenRes.setUri(res);
        return cloudAccountOPenRes;
    }


    @Override
    protected Map<String, Object> buildDataModel(Object... data) {
        return super.buildDataModel(data);
    }

    private JSONObject decrypt(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException {
        SdKeysHelper sdKeysHelper = applicationContext.getBean(SdKeysHelper.class);
        JSONObject dataJson = JSONUtil.parseObj(str);
        String decryptKey = dataJson.getStr("encryptKey");
        dataJson.remove("encryptKey");
        byte[] decryptKeyBytes = Base64.decodeBase64(decryptKey);
        decryptKey = new String(RSAUtils.decrypt(decryptKeyBytes, sdKeysHelper.getPrivateKey()));
        log.info("RSA解密后随机数：{}", decryptKey);
        String encryptValue = dataJson.getStr("data");
        log.info("AES解密前值：{}", encryptValue);
        byte[] decryptDataBase64 = Base64.decodeBase64(encryptValue);
        byte[] decryptDataBytes = AESUtils.decrypt(decryptDataBase64, decryptKey.getBytes(StandardCharsets.UTF_8), (String) null);
        String decryptData = new String(decryptDataBytes);
        log.info("AES解密后值：{}", decryptData);
        return JSONUtil.parseObj(decryptData);
    }

    private boolean verifySign(String str) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        JSONObject dataJson = JSONUtil.parseObj(str);
        if (Objects.isNull(dataJson) || !dataJson.containsKey("data")) {
            return false;
        }
        SdKeysHelper sdKeysHelper = applicationContext.getBean(SdKeysHelper.class);
        String sign = dataJson.getStr("sign");
        String signType = dataJson.getStr("signType");
        dataJson.remove("sign");
        dataJson.remove("signType");
        String plainText = dataJson.getStr("data");
        return SignatureUtils.verify(plainText, sign, signType, sdKeysHelper.getCloudKey());
    }

    private void sign(JSONObject paramsJson) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        SdKeysHelper sdKeysHelper = applicationContext.getBean(SdKeysHelper.class);
        String plainText = paramsJson.getStr("data");
        log.info("待签名报文：{}", plainText);
        paramsJson.set("sign", SignatureUtils.sign(plainText, "SHA1WithRSA", sdKeysHelper.getPrivateKey()));
        paramsJson.set("signType", "SHA1WithRSA");
    }

    private JSONObject encrypt(String str) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
        SdKeysHelper sdKeysHelper = applicationContext.getBean(SdKeysHelper.class);
        JSONObject encryptResult = JSONUtil.parseObj(str);
        String aesKey = RandomUtils.genRandomStringByLength(16);
        byte[] aesKeyBytes = aesKey.getBytes(StandardCharsets.UTF_8);
        log.info("生成加密随机数：{}", aesKey);
        String plainValue = encryptResult.toString();
        log.info("AES加密前值：{}", plainValue);
        byte[] encryptValueBytes = AESUtils.encrypt(plainValue.getBytes(StandardCharsets.UTF_8), aesKeyBytes, (String) null);
        String encryptValue = new String(Base64.encodeBase64(encryptValueBytes));
        log.info("AES加密后值：{}", encryptValue);
        encryptResult.clear();
        encryptResult.set("data", encryptValue);
        byte[] encryptKeyBytes = RSAUtils.encrypt(aesKeyBytes, sdKeysHelper.getCloudKey());
        String sandEncryptKey = new String(Base64.encodeBase64(encryptKeyBytes));
        encryptResult.set("encryptKey", sandEncryptKey);
        encryptResult.set("encryptType", "AES");
        return encryptResult;
    }
}
