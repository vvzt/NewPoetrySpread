package com.xuebling.newpoetryspread.pojo.result;

import com.xuebling.newpoetryspread.pojo.enums.ResponseMsg;


//响应体,有数据
public class ResponseData extends Response {
    private Object data;
    public ResponseData(ResponseMsg msg, Object data){
        super(msg);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
