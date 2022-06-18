package com.starnft.star.common.utils;


public class DesensitizedUtils{


    public static final String REGEX_TEMPLATE = "(?<=\\w{%d})\\w(?=\\w{%d})";
    private static final String SYMBOL = "*";

    public static String backCard(String bankCard){
        return replaceValue(bankCard,4,3);
    }

    public static String replaceValue(String value,int left,int right){
        if (value == null || (value.length() < left + right + 1))
            return value;

        String regex = String.format(REGEX_TEMPLATE,left,right);
        return value.replaceAll(regex,SYMBOL);
    }

    public static void main(String[] args) {
        System.out.println(backCard("6225758268281192"));
    }


}
