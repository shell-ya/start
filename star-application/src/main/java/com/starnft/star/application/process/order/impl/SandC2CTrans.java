package com.starnft.star.application.process.order.impl;

import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

//TODO 修改商户号，mer_key(key1),key(md5key)

/**
 * 云账户C2C消费-04010003
 */
@Slf4j
public class SandC2CTrans {
    //
    // public static void main(String[] args) {
    //
    //     OrderPayReq orderPayReq = new OrderPayReq();
    //     orderPayReq.setUserId(509057525L);
    //     orderPayReq.setOwnerId("536952750");
    //     orderPayReq.setOrderSn("TS1041023318955855872");
    //     orderPayReq.setPayAmount("0.01");
    //     orderPayReq.setTotalPayAmount("0.01");
    //     orderPayReq.setNumberId(1019309563523076096L);
    //     orderPayReq.setThemeId(1019309563523076096L);
    //     orderPayReq.setSeriesId(9L);
    //     orderPayReq.setType(3);
    //     orderPayReq.setCategoryType(1);
    //     orderPayReq.setReturnUri("https://www.baidu.com");
    //     System.out.println(JSONUtil.toJsonStr(orderPayReq));
    // }

    /**
     * 构建2C2转账链接
     * @param param
     * @return
     */
    public static String buildTransUrl(C2CTransParam param ) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String createTime = sdf.format(calendar.getTime());
        calendar.add(Calendar.HOUR, 1);
        String endTime = sdf.format(calendar.getTime());

        String version = "10";
        // 商户号
        String mer_no = "6888806044846";

        // 商户key1
        String mer_key = "b5ZRGge5REwLvaBsgvYzIHvWw2yx4+FGTWmKR93Vagm6lSM5Qa5EisCPVf2jNEDDFJoLd+tg+HY=";

        // 订单号
        // String mer_order_no = RandomUtil.randomString(20);
        String mer_order_no = param.getMer_order_no();

        // 回调地址
        // String notify_url = "https://474dab98.cpolar.top/notify";
        String notify_url = param.getNotify_url();

        // String return_url = "https://www.baidu.com";
        String return_url = param.getReturn_url();

        // 金额
        // String order_amt = "0.12";
        String order_amt = param.getOrder_amt();

        // 商品名称
        String goods_name = "市场订单支付";
        // 支付扩展域
        // "operationType":"操作类型",//  1:转账申请 2:确认收款 3:转账退回
        // "recvUserId":"收款方会员编号", //所有操作类型必填参数
        // "remark":"备注" //非必填

        // 当operationType为1时参数按照下面说明填：return_url,notify_url为必填参数
        // "bizType":"转账类型", //必填 1：转账确认模式 2：实时转账模式
        // "payUserId":"付款方会员编号，用户在商户系统中的唯一编号 ；",//必填
        // "userFeeAmt":"用户服务费，商户向用户收取的服务费 ",//非必填
        // "postscript":"附言",// 非必填
        String pay_extra = "{\"operationType\":\"1\",\"recvUserId\":\"" + param.getRecvUserId() + "\",\"bizType\":\"2\",\"payUserId\":\"" + param.getPayUserId() + "\",\"remark\":\"市场订单支付\"}";

        //md5key
        String key = "Mvb16HX1dERkURx2049aMmD8iK1v5w68dEwwmoU0fCieS8g6pb381Okrr5TWHR9b/Vmmz6scR/043v2K3BB4ED8cBcNRUfk3HRJQIn57Zk4xpad2fR6DoCUvKgWklkNTtGHiO2ZDIudixuz+UKFTiw==";

        Map<String, String> map = new LinkedHashMap<>();
        map.put("accsplit_flag", "NO");
        map.put("create_ip", "127_0_0_1");
        map.put("create_time", createTime);

        map.put("mer_key", mer_key);
        map.put("mer_no", mer_no);
        map.put("mer_order_no", mer_order_no);
        map.put("notify_url", notify_url);
        map.put("order_amt", order_amt);
        map.put("pay_extra", pay_extra);
        map.put("return_url", return_url);
        map.put("sign_type", "MD5");
        map.put("store_id", "000000");
        map.put("version", version);
        map.put("key", key);

       // map.put("expire_time",endTime);
       // map.put("goods_name",goods_name);
       // map.put("product_code","02010006");
       // map.put("clear_cycle","0");

        String signature = "";

        for (String s : map.keySet()) {
            if (!(map.get(s) == null || map.get(s).equals(""))) {
                signature += s + "=";
                signature += map.get(s) + "&";
            }
        }
        signature = signature.substring(0, signature.length() - 1);
        System.out.println("参与签名字符串：\n" + signature);

        String sign = SandMD5Util.encode(signature).toUpperCase();

        System.out.println("签名串：\n" + sign);


        // 拼接url
        String url = "https://faspay-oss.sandpay.com.cn/pay/h5/cloud?" +
                // 云函数h5： applet  ；支付宝H5：alipay  ； 微信公众号H5：wechatpay   ；
                // 一键快捷：fastpayment   ；H5快捷 ：unionpayh5    ；支付宝扫码：alipaycode ;快捷充值:quicktopup
                // 电子钱包【云账户】：cloud
                "version=" + version + "" +
                "&mer_no=" + mer_no + "" +
                "&mer_key=" + URLEncoder.encode(mer_key) + "" +
                "&mer_order_no=" + mer_order_no + "" +
                "&create_time=" + createTime + "" +
                "&expire_time=" + endTime + "" +  //endTime
                "&order_amt=" + order_amt + "" +
                "&notify_url=" + URLEncoder.encode(notify_url) + "" +
                "&return_url=" + URLEncoder.encode(return_url) + "" +
                "&create_ip=127_0_0_1" +
                "&goods_name=" + URLEncoder.encode(goods_name) + "" +
                "&store_id=000000" +
                // 产品编码: 云函数h5：  02010006  ；支付宝H5：  02020002  ；微信公众号H5：02010002   ；
                // 一键快捷：  05030001  ；H5快捷：  06030001   ；支付宝扫码：  02020005 ；快捷充值：  06030003
                // 电子钱包【云账户】：开通账户并支付product_code应为：04010001；消费（C2C）product_code 为：04010003 ; 我的账户页面 product_code 为：00000001
                "&product_code=04010003" + "" +
                "&clear_cycle=3" +
                "&pay_extra=" + URLEncoder.encode(pay_extra) + "" +
                "&meta_option=%5B%7B%22s%22%3A%22Android%22,%22n%22%3A%22wxDemo%22,%22id%22%3A%22com.pay.paytypetest%22,%22sc%22%3A%22com.pay.paytypetest%22%7D%5D" +
                "&accsplit_flag=NO" +
                "&jump_scheme=" +
                "&sign_type=MD5" +
                "&sign=" + sign + "";
        log.info("最终链接：\n\n" + url);

        return url;

    }

}
