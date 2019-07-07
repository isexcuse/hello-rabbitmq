package com.framework.rabbitmq.vo.response.api;
/**
 * @description: 所有接口返回数据的携带对象
 * @author: XiongFeiYang
 * @createTime: 2019-01-23 10:21
 **/

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回CODE码，详见{@link ApiConst.Code}中常量定义
     */
    private int code;

    /**
     * 异常返回message
     */
    private String msg;

    /**
     * 成功返回数据
     */
    private T data;

    public ApiResponse() {

    }

    /**
     * 构建成功返回数据对象
     *
     * @param data 返回数据内容
     * @return
     */
    public static <T> ApiResponse<T> buildSuccessResponse(T data) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setCode(ApiConst.Code.CODE_SUCCESS.code());
        response.setData(data);
        return response;
    }

    /**
     * 构建空返回对象
     *
     * @return
     */
    public static <T> ApiResponse<T> buildEmptyResponse() {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setCode(ApiConst.Code.CODE_CONTENT_EMPTY.code());
        return response;
    }

    /**
     * 构建常规错误返回对象
     *
     * @param msg
     * @return
     */
    public static <T> ApiResponse<T> buildCommonErrorResponse(String msg) {
        return buildResponse(ApiConst.Code.CODE_COMMON_ERROR, msg);
    }

    /**
     * 构建自定义CODE码对象
     *
     * @param code
     * @param data
     * @param msg
     * @return
     */
    public static <T> ApiResponse<T> buildResponse(int code, T data, String msg) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setCode(code);
        response.setData(data);
        response.setMsg(msg);
        return response;
    }

    /**
     * 构建CODE码和提示信息对象
     *
     * @param code
     * @param msg
     * @return
     */
    public static <T> ApiResponse<T> buildResponse(ApiConst.Code code, String msg) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setCode(code.code());
        response.setMsg(msg);
        return response;
    }

    /**
     * 构建用户自定义CODE码和提示信息对象
     *
     * @param code
     * @param msg
     * @return
     */
    public static <T> ApiResponse<T> buildResponse(int code, String msg) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    /**
     * 是否执行成功
     *
     * @return
     */
    @JsonIgnore
    public boolean isSuccess() {
        return ApiConst.Code.CODE_SUCCESS.code() == this.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ApiResponse [code=").append(code).append(", msg=").append(msg).append(", data=").append(data)
                .append("]");
        return builder.toString();
    }

}
