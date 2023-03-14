package com.dada.dm.qujia.exception;

/**
 * @description 业务异常 
 * @className AbstractCodeException
 * @packageName com.dada.dm.qujia.exception
 * @author jt.Qu
 * @date 2023/3/14 10:39
 */

public abstract class AbstractCodeException extends RuntimeException {
    private static final long serialVersionUID = -8791057210176151842L;
    private int code;
    private String message;

    public AbstractCodeException(int code, String message){
        super(message);
        this.code = code;
        this.message = message;

    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
