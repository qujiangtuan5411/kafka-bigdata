package com.dada.dm.qujia.model;



import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 3112915027924948320L;
    private int code;
    private String msg;
    private T body;

    public Response(){

    }

    public Response(int code, String msg, T body){
        this.code = code;
        this.body = body;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
