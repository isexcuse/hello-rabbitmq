package com.framework.rabbitmq.vo.response.api;

/**
 * 返回CODE码定义, 按照httpstatus的code码定义, 平台自定义的为四位数
 */
public enum ApiStatusEnum {
    /**
     * 系统异常
     */
    SYSTEM_ERR(-1, "系统异常"),

    /**
     * 处理成功
     */
    SUCCESS(200, "成功"),

    /**
     * 参数错误:301
     */
    PARAM_ERR(301, "参数不合法"),

    /**
     * 登录权限相关
     */
    LOGIN_HAS_EXPIRED(10001, "登录已过期"),
    NO_PERMISSION_ACCESS(10002, "无权限访问"),
    ;


    private Integer code;
    private String msg;

    ApiStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
