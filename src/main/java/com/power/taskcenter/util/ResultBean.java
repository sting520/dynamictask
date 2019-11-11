package com.power.taskcenter.util;

import java.io.Serializable;

public class ResultBean implements Serializable {

    private static final long serialVersionUID = -8276264968757808344L;

    private static final int SUCCESS = 0;

    public static final int FAIL = -1;

    private String msg = "操作成功";

    private int code = SUCCESS;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    private Long count;

    private Object data;

    private ResultBean() {
        super();
    }


    private ResultBean(String msg, Object data, int code,Long count) {
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.count = count;
    }

    public static ResultBean success() {
        return success("操作成功");
    }

    public static ResultBean success(String msg) {
        return success(msg, null,0L);
    }

    public static ResultBean successData(Object data) {
        return success("操作成功", data,0L);
    }

    public static ResultBean successData(Long count,Object data) {
        return success("操作成功", data,count);
    }


    public static ResultBean success(Object data) {
        return success("操作成功", data,0L);
    }

    public static ResultBean success(String msg, Object data,Long count) {
        return new ResultBean(msg, data, SUCCESS,count);
    }

    public static ResultBean error(String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(FAIL);
        resultBean.setMsg(msg);
        return resultBean;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
