package com.starnft.star.domain.captcha.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifyResult {

    /**
     * 异常代号
     */
    private int error;
    /**
     * 错误描述信息
     */
    private String msg;
    /**
     * 二次校验结果 true:校验通过 false:校验失败
     */
    private boolean result;

    private String token;

    /**
     * 短信上行发送的手机号码
     * 仅限于短信上行的验证码类型
     */
    private String phone;

    /**
     * 额外字段
     */
    private String extraData;


    public static VerifyResult fakeFalseResult(String resp) {
        VerifyResult result = new VerifyResult();
        result.setResult(false);
        result.setError(0);
        result.setMsg(resp);
        result.setPhone("");
        return result;
    }

    public static VerifyResult fakeTrueResult(String resp) {
        VerifyResult result = new VerifyResult();
        result.setResult(true);
        result.setError(0);
        result.setMsg(resp);
        result.setPhone("");
        return result;
    }
}
