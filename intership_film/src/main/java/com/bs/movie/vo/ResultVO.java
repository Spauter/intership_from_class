package com.bs.movie.vo;

import com.bs.movie.enums.ResultEnum;

/**
 * @description: 结果视图类
 * @author: Lydia
 * @create: 2023-05-23 21:28
 */

public class ResultVO {
    private Integer code;
    private String msg;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultVO() {
    }

    public ResultVO(ResultEnum enums) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public ResultVO(ResultEnum enums, Object data) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.data = data;
    }

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
