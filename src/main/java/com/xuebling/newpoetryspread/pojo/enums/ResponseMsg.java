package com.xuebling.newpoetryspread.pojo.enums;


//返回信息的枚举类
public enum ResponseMsg {
    SUCCESS("000", "操作成功"),
    ONEDONE("003","文件片上传成功"),
    ALLDONE("004","整个文件上传成功"),
    SURPRISE("005","秒传成功"),
    TASKUNFINISHED("006","任务已存在但未完成"),
    CHUNKDONE("301","该文件块已被上传"),
    DATAILLEGAL("900","请求携带的数据不合法"),
    FIELDNONE("994","要修改的字段不存在"),
    TARGETDONE("995","文件块已经被上传"),
    ONEFAIL("996","文件片上传失败"),
    ALLFAIL("997","整个文件上传失败"),
    TARGETNULL("998","请求的对象不存在"),
    FAIL("999","操作失败"),
    ;
    ResponseMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
