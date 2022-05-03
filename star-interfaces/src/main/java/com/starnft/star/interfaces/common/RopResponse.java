package com.starnft.star.interfaces.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RopResponse<T> {

    @ApiModelProperty("返回码 0-成功")
    private int code;

    @ApiModelProperty("错误消息")
    private String msg;

    @ApiModelProperty("数据")
    private T data;

    /**
     * 成功，不返回具体数据
     *
     * @param <T>
     * @return
     */
    public static <T> RopResponse<T> successNoData() {
        RopResponse<T> result = new RopResponse<T>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg("Success");
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    /**
     * 成功，返回数据
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> RopResponse<T> success(T t) {
        RopResponse<T> result = new RopResponse<T>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg("Success");
        result.setData(t);
        return result;
    }

    /**
     * 失败，返回失败信息
     *
     * @param code
     * @param <T>
     * @return
     */
    public static <T> RopResponse<T> fail(ResultCode code) {
        RopResponse<T> result = new RopResponse<T>();
        result.setCode(code.getCode());
        result.setMsg("Fail");
        result.setMsg(code.getMsg());
        return result;
    }

    /**
     * 失败，返回失败信息
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> RopResponse<T> fail(String msg) {
        RopResponse<T> result = new RopResponse<T>();
        result.setCode(ResultCode.COMMON_ERROR.getCode());
        result.setMsg("Fail");
        result.setMsg(msg);
        return result;
    }
}