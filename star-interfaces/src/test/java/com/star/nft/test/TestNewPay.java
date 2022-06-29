package com.star.nft.test;

import cn.hutool.core.util.IdUtil;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.payment.core.IPaymentService;
import com.starnft.star.domain.payment.model.req.PaymentRich;
import com.starnft.star.domain.payment.model.res.PaymentRes;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest(classes = {StarApplication.class})
public class TestNewPay {
    @Resource
    IPaymentService paymentService;
@Test
    public  void testcheckPay(){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("userName","黄坤煌");
//        stringObjectHashMap.put("idCard","445122199510295218");
        PaymentRich req = PaymentRich.builder()
                .totalMoney(new BigDecimal("0.15")).payChannel(StarConstants.PayChannel.CloudAccount.name())
                .frontUrl("http://localhost:1025").clientIp("192.168.1.1")
                .orderSn(IdUtil.getSnowflake(1, 1).nextIdStr())
                .userId(1L)
                .payExtend(stringObjectHashMap)
                .orderType(StarConstants.OrderType.RECHARGE).build();
//        System.out.println(req.composeCallback());

        PaymentRes union_pay = paymentService.pay(req);
        System.out.println(union_pay);
    }
@Test
public void testNewPay(){
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
            Calendar calendar = Calendar.getInstance();
            String createTime = sdf.format(calendar.getTime());
            calendar.add(Calendar.HOUR,1);
            String endTime = sdf.format(calendar.getTime());

            String version = "10";
            //商户号
            String mer_no = "6888806044846";
            //商户key1
            String mer_key = "b5ZRGge5REwLvaBsgvYzIHvWw2yx4+FGTWmKR93Vagm6lSM5Qa5EisCPVf2jNEDDFJoLd+tg+HY=";
            //订单号
            UUID uuid = UUID.randomUUID();
            String mer_order_no = uuid.toString().replaceAll("-","");
            //回调地址
            String notify_url = "https://www.baidu.com";
            String return_url = "https://www.baidu.com";
            //金额
            String order_amt = "0.11";
            //商品名称
            String goods_name = "测试";
            //支付扩展域
            //云函数所需参数，resourceAppid：小程序AppID； resourceEnv：云开发环境 ID
            String pay_extra = "{\"userId\":\"1\",\"userName\":\"黄坤煌\",\"idCard\":\"445122199510295218\"}";
        //    String pay_extra = "{\"userId\":\"1\"}";

            //md5key
            String key = "Mvb16HX1dERkURx2049aMmD8iK1v5w68dEwwmoU0fCieS8g6pb381Okrr5TWHR9b/Vmmz6scR/043v2K3BB4ED8cBcNRUfk3HRJQIn57Zk4xpad2fR6DoCUvKgWklkNTtGHiO2ZDIudixuz+UKFTiw==";
            //产品编码02010006所需参数
//            String gh_static_url="https://wx2adc88e57776-1310195393.tcloudbaseapp.com/jump_mp.html";

            Map<String, String> map = new LinkedHashMap<>();
            map.put("accsplit_flag","NO");
            map.put("create_ip","127_0_0_1");
            map.put("create_time",createTime);
            map.put("mer_key",mer_key);
            map.put("mer_no",mer_no);
            map.put("mer_order_no",mer_order_no);
            map.put("notify_url",notify_url);
            map.put("order_amt",order_amt);
            map.put("pay_extra",pay_extra);
            map.put("return_url",return_url);
            map.put("sign_type","MD5");
            map.put("store_id","000000");
            map.put("version",version);
            map.put("key",key);


//        map.put("expire_time",endTime);
//        map.put("goods_name",goods_name);
//        map.put("product_code","02010006");
//        map.put("clear_cycle","0");

            String signature = "";

            for (String s : map.keySet()){
                if(!(map.get(s)==null||map.get(s).equals(""))){
                    signature+=s+"=";
                    signature+=map.get(s)+"&";
                }
            }
            signature = signature.substring(0,signature.length()-1);
            System.out.println("参与签名字符串：\n"+signature);

            String sign = MD5Util.encode(signature).toUpperCase();

            System.out.println("签名串：\n"+sign);


            //拼接url
            String url = "https://sandcash.mixienet.com.cn/pay/h5/quicktopup?" +
                    //  String url = "https://sandcash.mixienet.com.cn/pay/h5/fastpayment?" +
//     云函数h5： applet  ；支付宝H5：alipay  ； 微信公众号H5：wechatpay   ；
// 一键快捷：fastpayment   ；H5快捷 ：unionpayh5    ；支付宝扫码：alipaycode ;快捷充值:quicktopup
//电子钱包【云账户】：cloud
                    "version="+version+"" +
                    "&mer_no="+mer_no+"" +
                    "&mer_key="+ URLEncoder.encode(mer_key)+"" +
                    "&mer_order_no="+mer_order_no+"" +
                    "&create_time="+createTime+"" +
                    "&expire_time="+endTime+"" +  //endTime
                    "&order_amt=0.11" +
                    "&notify_url="+URLEncoder.encode(notify_url)+"" +
                    "&return_url="+URLEncoder.encode(return_url)+"" +
                    "&create_ip=127_0_0_1" +
                    "&goods_name="+URLEncoder.encode(goods_name)+"" +
                    "&store_id=000000" +
// 产品编码: 云函数h5：  02010006  ；支付宝H5：  02020002  ；微信公众号H5：02010002   ；
//一键快捷：  05030001  ；H5快捷：  06030001   ；支付宝扫码：  02020005 ；快捷充值：  06030003
//电子钱包【云账户】：开通账户并支付product_code应为：04010001；消费（C2C）product_code 为：04010003 ; 我的账户页面 product_code 为：00000001
                    "&product_code=06030003" +
                    "&clear_cycle=3" +
                    "&pay_extra="     +URLEncoder.encode(pay_extra)+""+
                    "&meta_option="+URLEncoder.encode("[{\"s\":\"Android\",\"n\":\"CircleMeta\",\"id\":\"cn.CircleMeta.app\",\"sc\":\"/index\"},{\"s\":\"IOS\",\"n\":\"CircleMeta\",\"id\":\"cn.CircleMeta.app\",\"sc\":\"/index\"}]") +
                    "&accsplit_flag=NO" +
                    "&jump_scheme=" +
//                    "&gh_static_url="+gh_static_url+""+
                    "&sign_type=MD5" +
                    "&sign="+sign+"" ;

            System.out.println("最终链接：\n\n"+url);

        }


    }



    class MD5Util {


        /**
         * 私有构造方法,将该工具类设为单例模式.
         */
        private MD5Util() {
        }

        private static final String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

        public static String encode(String password) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] byteArray = md5.digest(password.getBytes("utf-8"));
                String passwordMD5 = byteArrayToHexString(byteArray);
                return passwordMD5;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return password;
        }

        public static String encode(String password, String enc) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] byteArray = md5.digest(password.getBytes(enc));
                String passwordMD5 = byteArrayToHexString(byteArray);
                return passwordMD5;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return password;
        }

        private static String byteArrayToHexString(byte[] byteArray) {
            StringBuffer sb = new StringBuffer();
            for (byte b : byteArray) {
                sb.append(byteToHexChar(b));
            }
            return sb.toString();
        }

        private static Object byteToHexChar(byte b) {
            int n = b;
            if (n < 0) {
                n = 256 + n;
            }
            int d1 = n / 16;
            int d2 = n % 16;
            return hex[d1] + hex[d2];
        }





}
