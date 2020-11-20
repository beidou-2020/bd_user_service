package com.bd.entitys.enumerate;

public enum ResultCode {

    /**
     * 通用
     */
    SUCCESS(0, "成功"),
    AUTH_FAIL(1, "身份认证不通过"),
    NOT_FOUND(2, "请求的信息不存在"),
    PARAM_ERROR(3, "请求所使用的参数无效"),
    REFUSE_REQUEST(12, "接口关闭"),
    ERROR(99, "服务端错误"),
    REQUEST_SUCCESS(200,"请求成功"),
    SERVER_ERROR(500,"服务器异常"),
    RESPONSE__NULL(506, "下游服务返回为空"),
    RESPONSE_BODY_NULL(507, "下游服务返回body体为空"),
    RESPONSE_CODE_NULL(508, "下游服务返回code为空"),
    RESPONSE_NULL(509, "下游服务response为空"),
    PARAM_WRONG(1001, "参数错误,请检查数据重新操作！");

    ResultCode(int code , String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int code() {
        return this.code;
    }
    public String msg(){
        return this.msg;
    }
}
