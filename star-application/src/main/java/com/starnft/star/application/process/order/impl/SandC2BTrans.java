package com.starnft.star.application.process.order.impl;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户消费（C2B） 04010001
 */
@Slf4j
public class SandC2BTrans {

    public static String buildTransUrl(C2BTransParam param) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String createTime = sdf.format(calendar.getTime());
        calendar.add(Calendar.HOUR, 1);
        String endTime = sdf.format(calendar.getTime());

        String version = "10";
        //商户号
        String mer_no = "6888806044846";

        //商户key1
        String mer_key = "b5ZRGge5REwLvaBsgvYzIHvWw2yx4+FGTWmKR93Vagm6lSM5Qa5EisCPVf2jNEDDFJoLd+tg+HY=";

        //订单号
        String mer_order_no = RandomUtil.randomString(20);

        //回调地址
        // String notify_url = "https://www.baidu.com";
        String notify_url = param.getNotify_url();

        // String return_url = "https://www.baidu.com";
        String return_url = param.getReturn_url();

        //金额
        // String order_amt = "0.01";
        String order_amt = param.getOrder_amt();

        //商品名称
        String goods_name = "市场订单支付";

        //支付扩展域
        //"userId":"用户在商户系统中的唯一编号", "nickName":"会员昵称","accountType"："账户类型"  （选填）
        String pay_extra = "{\"userId\":\"" + param.getPayUserId() + "\",\"nickName\":\"张三\",\"accountType\":\"1\"}";

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

        //拼接url
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
                "&product_code=04010001" + "" +
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

