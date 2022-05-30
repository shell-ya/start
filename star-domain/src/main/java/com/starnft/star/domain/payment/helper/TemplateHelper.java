package com.starnft.star.domain.payment.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class TemplateHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 线程安全单例模式生成TemplateHelper实体
     */
    private static volatile TemplateHelper instance = null;

    public static TemplateHelper getInstance() {
        if (instance == null) {
            synchronized (TemplateHelper.class) {
                if (instance == null) {
                    instance = new TemplateHelper();
                }
            }
        }
        return instance;
    }


    /**
     * 将数据模型中的属性值转换为字符串
     *
     * @param dataModel
     * @return
     */
    public String getDataModelString(Object dataModel) {
        StringBuilder sb = new StringBuilder();
        String className = dataModel.getClass().getName();
        sb.append(className).append("{");
        try {
            Class<?> clazz = Class.forName(className);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                sb.append(field.getName()).append("=").append(field.get(dataModel)).append(",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("}");
        return sb.toString();
    }


    /**
     * 判断json是否为数组类型
     *
     * @param object
     * @return
     */
    public static boolean isArray(Object object) {
        // 判断获取的参数类型是否为数组或者为list集合
        Object o = JSON.parse(JSON.toJSONString(object));
        if (isNullOrEmpty(o)) {
            return Boolean.FALSE;
        }
        if (o instanceof JSONObject) {
            return Boolean.FALSE;
        } else if (o instanceof JSONArray) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 判断一个未知对象是否为空
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null || "null".equals(obj))
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }
    public BigDecimal fenToYuan(Object o) {
        BigDecimal bigDecimal = new BigDecimal(o.toString());

   return  bigDecimal.divide(new BigDecimal(100)).setScale(2);
    }
    public String yuanToFen(Object o) {
        if (o == null)
            return "0";
        String s = o.toString();
        int posIndex = -1;
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")) {
            posIndex = s.indexOf(".");
            if (posIndex > 0) {
                int len = s.length();
                if (len == posIndex + 1) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append("00");
                } else if (len == posIndex + 2) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 2)).append("0");
                } else if (len == posIndex + 3) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                } else {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                }
            } else {
                sb.append(s).append("00");
            }
        } else {
            sb.append("0");
        }
        str = removeZero(sb.toString());
        if (str != null && str.trim().length() > 0 && !str.trim().equalsIgnoreCase("null")) {
            return String.format("%012d", Integer.parseInt(str));
        } else {
            return "0";
        }
    }

    private String removeZero(String str) {
        char ch;
        String result = "";
        if (str != null && str.trim().length() > 0 && !str.trim().equalsIgnoreCase("null")) {
            try {
                for (int i = 0; i < str.length(); i++) {
                    ch = str.charAt(i);
                    if (ch != '0') {
                        result = str.substring(i);
                        break;
                    }
                }
            } catch (Exception e) {
                result = "";
            }
        } else {
            result = "";
        }
        return result;

    }

    /**
     * 将形如key=value&key=value的字符串转换为相应的Map对象
     *
     * @param result
     * @return
     */
    public Map<String, String> convertResultStringToMap(String result) {
        Map<String, String> map = null;
        try {

            if (StringUtils.isNotBlank(result)) {
                if (result.startsWith("{") && result.endsWith("}")) {
                    System.out.println(result.length());
                    result = result.substring(1, result.length() - 1);
                }
                map = parseQString(result);
            }

        } catch (UnsupportedEncodingException e) {
            logger.error("转换失败",e);
        }
        return map;
    }

    /**
     * 解析应答字符串，生成应答要素
     *
     * @param str 需要解析的字符串
     * @return 解析的结果map
     * @throws UnsupportedEncodingException
     */
    private Map<String, String> parseQString(String str) throws UnsupportedEncodingException {

        Map<String, String> map = new HashMap<String, String>();
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        char curChar;
        String key = null;
        boolean isKey = true;
        boolean isOpen = false;// 值里有嵌套
        char openName = 0;
        if (len > 0) {
            for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
                curChar = str.charAt(i);// 取当前字符
                if (isKey) {// 如果当前生成的是key

                    if (curChar == '=') {// 如果读取到=分隔符
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                } else {// 如果当前生成的是value
                    if (isOpen) {
                        if (curChar == openName) {
                            isOpen = false;
                        }

                    } else {// 如果没开启嵌套
                        if (curChar == '{') {// 如果碰到，就开启嵌套
                            isOpen = true;
                            openName = '}';
                        }
                        if (curChar == '[') {
                            isOpen = true;
                            openName = ']';
                        }
                    }
                    if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
                        putKeyValueToMap(temp, isKey, key, map);
                        temp.setLength(0);
                        isKey = true;
                    } else {
                        temp.append(curChar);
                    }
                }

            }
            putKeyValueToMap(temp, isKey, key, map);
        }
        return map;
    }

    private void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, Map<String, String> map) {
        if (isKey) {
            key = temp.toString();
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, "");
        } else {
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, temp.toString());
        }
    }


    public String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public String outLineTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        return new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
    }


    public String decimalToString(BigDecimal decimal){
        return decimal.toPlainString();
    }


    public String escapeStr(String str){
        char[] chars = new JsonStringEncoder().quoteAsString(str);
        return String.valueOf(chars).replace("\\\\","");
    }
}
