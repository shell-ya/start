package com.starnft.star.common;

import java.io.Serializable;

/**
 * @description: 统一返回对象中，Code码、Info描述
 * @date: 2022/5/7
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -3826891916021780628L;
    private Integer code;
    private String info;

    public static Result buildResult(ResultCode code) {
        return new Result(code.getCode(), code.getMsg());
    }

    public static Result buildResult(ResultCode code, String info) {
        return new Result(code.getCode(), info);
    }

    public static Result buildResult(Integer code, String info) {
        return new Result(code, info);
    }

    public static Result buildResult(ResultCode code, ResultCode info) {
        return new Result(code.getCode(), info.getMsg());
    }

    public static Result buildSuccessResult() {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }

    public static Result buildErrorResult() {
        return new Result(ResultCode.SYSTEM_INNER_ERROR.getCode(), ResultCode.SYSTEM_INNER_ERROR.getMsg());
    }

    public static Result buildErrorResult(String info) {
        return new Result(ResultCode.SYSTEM_INNER_ERROR.getCode(), info);
    }

    public Result(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
