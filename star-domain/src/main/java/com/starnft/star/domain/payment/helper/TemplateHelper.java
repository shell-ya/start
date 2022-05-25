package com.starnft.star.domain.payment.helper;

public class TemplateHelper {
    private static TemplateHelper instance = new TemplateHelper();

    public static TemplateHelper getInstance() {
        return instance;
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
}
