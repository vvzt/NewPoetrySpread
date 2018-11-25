package com.xuebling.newpoetryspread.pojo.result;

import com.xuebling.newpoetryspread.pojo.enums.ResponseMsg;

//响应体,无数据
public class Response {
    //默认构造函数
    Response(){

    }
    //根据异常类构造
    public Response(ResponseMsg msg){
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }
    /** 返回信息码*/
    private String code="000";
    /** 返回信息内容*/
    private String msg="操作成功";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
