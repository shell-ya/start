package com.starnft.star.domain.payment.handler.sandpay;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.payment.helper.TemplateHelper;
import com.starnft.star.domain.payment.model.req.*;
import com.starnft.star.domain.payment.model.res.*;
import com.starnft.star.domain.support.process.assign.TradeType;
import com.starnft.star.domain.support.process.config.TempConf;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@Slf4j
public class SandPayCloudPayPaymentHandler extends AbstractSandPayCloudPayHandler {
    @Override
    public StarConstants.PayChannel getPayChannel() {
        return StarConstants.PayChannel.CloudAccount;
    }

    @Override
    public StarConstants.Pay_Vendor getVendor() {
        return StarConstants.Pay_Vendor.SandPay;
    }

    @Override
    protected RefundRes doRefund(RefundReq refundReq, Map<String, String> vendorConf) {
        return super.doRefund(refundReq, vendorConf);
    }

    @Override
    public CloudAccountStatusRes accountStatus(CloudAccountStatusReq cloudAccountStatusReq) {
        return super.accountStatus(cloudAccountStatusReq);
    }

    @Override
    public CloudAccountBalanceRes queryBalance(CloudAccountBalanceReq cloudAccountBalanceReq) {
        return super.queryBalance(cloudAccountBalanceReq);
    }

    @Override
    protected void verifyLegality(PaymentRich req) {
        Map<String, Object> payExtend = req.getPayExtend();
        if (!req.getOrderType().equals(StarConstants.OrderType.MARKET_GOODS)){
            Assert.notNull(payExtend, () -> new StarException("在PayExtend中加入nickName为用户的昵称"));
            Assert.notNull(payExtend.get("nickName"), () -> new StarException("在PayExtend中加入nickName为用户的昵称"));
            Assert.notNull(req.getUserId(), () -> new StarException("用户ID不能为空"));
        }else{
            Assert.notNull(payExtend, () -> new StarException("在PayExtend中加入accUserId为收款用户"));
            Assert.notNull(payExtend.get("accUserId"), () -> new StarException("在PayExtend中加入accUserId为收款用户"));
            Assert.notNull(payExtend.get("cost"), () -> new StarException("在PayExtend中加入cost为收取手续费"));
            Assert.notNull(payExtend.get("remark"), () -> new StarException("在PayExtend中加入remark为描述信息"));
            Assert.notNull(req.getUserId(), () -> new StarException("用户ID不能为空"));
        }

    }

    @Override
    protected PaymentRes doPay(PaymentRich paymentRich, Map<String, String> vendorConf) {
        if (!paymentRich.getOrderType().equals(StarConstants.OrderType.MARKET_GOODS)) {
            TempConf channelConf = getChannelConf(TradeType.Cloud_Account_SandPay);
            String signTempPath = channelConf.getSignTempPath();
            TemplateHelper instance = TemplateHelper.getInstance();
            String startTime = instance.getStartTime();
            String endTime = instance.getEndTime();
            String signResult = super.processTemplate(signTempPath, paymentRich, vendorConf, startTime, endTime);
            signResult = StrUtil.removeAllLineBreaks(signResult);

            String sign = instance.encode(signResult).toUpperCase();

            vendorConf.put("sign", sign);
            vendorConf.put("checkBankPayExtend", JSONUtil.toJsonStr(paymentRich.getPayExtend()));
            if (Objects.isNull(paymentRich.getPlatform())) {
                paymentRich.setPlatform(StarConstants.PlatformEnums.Web);
            }
            if (!paymentRich.getPlatform().equals(StarConstants.PlatformEnums.Web)) {
                String resTempPath = channelConf.getResTempPath();
                String result = super.processTemplate(resTempPath, paymentRich, vendorConf, startTime, endTime);
                result = StrUtil.removeAllLineBreaks(result);
                PaymentRes paymentRes = new PaymentRes();
                paymentRes.setThirdPage(result);
                paymentRes.setOrderSn(paymentRich.getOrderSn());
                paymentRes.setStatus(0);
                paymentRes.setTotalMoney(paymentRich.getTotalMoney().toString());
                paymentRes.setMessage("成功");
                return paymentRes;
            } else {
                String reqTempPath = channelConf.getReqTempPath();
                String resultUri = super.processTemplate(reqTempPath, paymentRich, vendorConf, startTime, endTime);
                PaymentRes paymentRes = new PaymentRes();
                resultUri = StrUtil.removeAllLineBreaks(resultUri);
                paymentRes.setGatewayApi(resultUri);
                paymentRes.setOrderSn(paymentRich.getOrderSn());
                paymentRes.setStatus(0);
                paymentRes.setTotalMoney(paymentRich.getTotalMoney().toString());
                paymentRes.setMessage("成功");
                return paymentRes;
            }
        } else {
            TempConf channelConf = getChannelConf(TradeType.Cloud_Account_C2C_Transfer_SandPay);
            String signTempPath = channelConf.getSignTempPath();
            TemplateHelper instance = TemplateHelper.getInstance();
            String startTime = instance.getStartTime();
            String endTime = instance.getEndTime();
            //封装支付参数
//            String payExtend = extractedPayExtend(paymentRich, vendorConf);
//            String payExtend =extractedRePayExtend()
            String payExtend = extractedTransferPayExtend(paymentRich);

            ///
            String signResult = super.processTemplate(signTempPath, paymentRich, vendorConf, startTime, endTime,payExtend);
            signResult = StrUtil.removeAllLineBreaks(signResult);
            String sign = instance.encode(signResult).toUpperCase();
            System.out.println("加密返回".concat(sign));
            vendorConf.put("sign", sign);
            String reqTempPath = channelConf.getReqTempPath();
            String resultUri = super.processTemplate(reqTempPath, paymentRich, vendorConf, startTime, endTime,payExtend);
            PaymentRes paymentRes = new PaymentRes();
            resultUri = StrUtil.removeAllLineBreaks(resultUri);
            System.out.println("最后的链接".concat(resultUri));
            paymentRes.setGatewayApi(resultUri);
            paymentRes.setOrderSn(paymentRich.getOrderSn());
            paymentRes.setStatus(0);
            paymentRes.setTotalMoney(paymentRich.getTotalMoney().toString());
            paymentRes.setMessage("成功");
            return paymentRes;

//           transfer
        }

    }

    private String extractedTransferPayExtend(PaymentRich paymentRich) {
        Map<String, Object> payExtends = paymentRich.getPayExtend();

//            {"operationType":"1",
//                    "recvUserId":"收款方会员编号",
//                    "bizType":"1",
//                    "payUserId":"aa11"
        String userId = paymentRich.getUserId().toString();
        String  accUserId = payExtends.get("accUserId").toString();
        BigDecimal cost = new BigDecimal(payExtends.get("cost").toString());
        String  remark = payExtends.get("remark").toString();
        HashMap<@Nullable String , @Nullable Object> extend = Maps.newHashMap();
        extend.put("operationType","1");
        extend.put("recvUserId",accUserId);
        extend.put("bizType","2");
        extend.put("payUserId",userId);
        extend.put("userFeeAmt",cost.toString());
        extend.put("postscript",remark);
        String payExtend = JSONUtil.toJsonStr(extend);
        return payExtend;
    }

    private String  extractedPayExtend(PaymentRich paymentRich,Map<String, String> vendorConf) {
        Map<String, Object> payExtend = paymentRich.getPayExtend();
        String userId = paymentRich.getUserId().toString();
        String  accUserId = payExtend.get("accUserId").toString();
        BigDecimal cost = new BigDecimal(payExtend.get("cost").toString());
        String  remark = payExtend.get("remark").toString();
        CloudC2CPayExtendReq cloudC2CPayExtendReq = new CloudC2CPayExtendReq();
        ArrayList< CloudC2CPayExtendReq.Payee> list = Lists.newArrayList();
        CloudC2CPayExtendReq.Payee payeeUser = new CloudC2CPayExtendReq.Payee();
        payeeUser.setRecvAmt(paymentRich.getTotalMoney().subtract(cost).toString());
        payeeUser.setRecvUserId(accUserId);
        payeeUser.setRecvCustomerOrderNo(paymentRich.getOrderSn().concat("A"));
        payeeUser.setRemark("售出：".concat(remark));
        CloudC2CPayExtendReq.Payee payeePlatform = new CloudC2CPayExtendReq.Payee();
        String mid = vendorConf.get("mid");
        payeePlatform.setRecvUserId(mid);
        payeePlatform.setRecvAmt(cost.toString());
        payeePlatform.setRecvCustomerOrderNo(paymentRich.getOrderSn().concat("P"));
        payeePlatform.setRemark("售出手续费：".concat(remark));
        list.add(payeeUser);
        list.add(payeePlatform);
        CloudC2CPayExtendReq.Payer payer = new CloudC2CPayExtendReq.Payer();
        payer.setPayUserId(userId);
        payer.setRemark("购买:".concat(remark));
        cloudC2CPayExtendReq.setPayer(payer);
        cloudC2CPayExtendReq.setPayeeList(list);
        cloudC2CPayExtendReq.setPayeeBizUserNo(accUserId);
        cloudC2CPayExtendReq.setOrderDesc(paymentRich.getOrderTypeDesc());
        return JSONUtil.toJsonStr(cloudC2CPayExtendReq);
    }


    @Override
    protected PayCheckRes doOrderCheck(PayCheckReq payCheckReq, Map<String, String> vendorConf) {
        return super.doOrderCheck(payCheckReq, vendorConf);
    }

    @Override
    protected Map<String, Object> buildDataModel(Object... data) {
        HashMap<@Nullable String, @Nullable Object> dataModel = Maps.newHashMap();
        dataModel.put("param1", data[0]);
        dataModel.put("param2", data[1]);
        dataModel.put("helper", TemplateHelper.getInstance());
        dataModel.put("createTime", data[2]);
        dataModel.put("endTime", data[3]);
        if (data.length >= 5) {
            dataModel.put("param3", data[4]);
        }
        if (data.length >= 6) {
            dataModel.put("param4", data[5]);
        }
        return dataModel;
    }
}
