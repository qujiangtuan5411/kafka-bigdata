package com.dada.dm.qujia.exception;


public enum ResponseCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "success"),
    FAIL(-1, "fail"),

    ACCESS_ERROR(1000,"达到访问次数限制，禁止访问"),

    ;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据枚举code获得对应value
     *
     * @param code
     * @return
     */
    public static ResponseCodeEnum getEnumByCode(Integer code) {
        for (ResponseCodeEnum resEnum : ResponseCodeEnum.values()) {
            if (code.equals(resEnum.getCode())) {
                return resEnum;
            }
        }
        return null;
    }
}
