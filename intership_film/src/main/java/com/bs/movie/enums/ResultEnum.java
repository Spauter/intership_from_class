package com.bs.movie.enums;

/**
 * 返回结果枚举
 *
 * @author yangting
 */
public enum ResultEnum {
    ERROR(500, "错误"),
    SUCCESS(200, "成功"),
    DATA_NULL(600, "暂无数据"),
    CODE_ERROR(501, "验证码错误"),
    CHECK_ERROR(502, "格式验证错误"),
    LOGIN_ERROR(601, "暂未登录");

    private Integer code;
    private String msg;

    private ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
