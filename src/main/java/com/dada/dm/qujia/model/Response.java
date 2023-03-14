package com.dada.dm.qujia.model;

import com.dada.dm.qujia.exception.ResponseCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @description  Response
 * @className Response
 * @packageName com.dada.dm.qujia.model
 * @author jt.Qu
 * @date 2023/3/14 10:41
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {
    public static final int SUCCESS_CODE = 200;

    public static final String SUCCESS = "success";
    
    private static final long serialVersionUID = -7782278672752498042L;

    private T body;

    private int code;

    private String msg;

    public Response(T body, int code, String msg) {
        this.body = body;
        this.code = code;
        this.msg = msg;
    }
    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(T body, ResponseCodeEnum codeEnum) {
        this.body = body;
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }


    public Response(ResponseCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public Response(T body) {
        this(body, ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg());
    }

    public static <T> Response<T> success() {
        return new Response<>(ResponseCodeEnum.SUCCESS);
    }

    public static <T> Response<T> success(T t) {
        return new Response<>(t, ResponseCodeEnum.SUCCESS);
    }

    public static <T> Response<T> success(T t, ResponseCodeEnum responseCodeEnum) {
        return new Response<>(t, responseCodeEnum);
    }

    public static Response<String> fail(ResponseCodeEnum responseCodeEnum) {
        return new Response<>(responseCodeEnum);
    }

    public static <T> Response<T> fail(T t) {
        return new Response<>(t, ResponseCodeEnum.FAIL);
    }

    public static <T> Response<T> fail(T t, ResponseCodeEnum responseCodeEnum) {
        return new Response<>(t, responseCodeEnum);
    }


    public static Response<String> fail(String message) {
        return new Response<>(ResponseCodeEnum.FAIL.getCode(), message);
    }


}
