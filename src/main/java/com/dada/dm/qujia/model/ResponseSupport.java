package com.dada.dm.qujia.model;

public class ResponseSupport {
    public static final int SUCCESS_CODE = 0;
    public static final int UNKNOWN_ERROR_CODE = -1;
    public static final String SUCCESS_MESSAGE = "success";

    public ResponseSupport() {
    }

    public static <K> Response<K> successResponse(K body) {
        return new Response<>(body,SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static <K> Response<K> failResponse(int code, String message) {
        return new Response<>(null,code, message);
    }

    public static <K> Response<K> unknownErrorResponse(String message) {
        return new Response<>(null,UNKNOWN_ERROR_CODE, message);
    }

    public static <K> boolean isSuccess(Response<K> response) {
        if (response == null) {
            return false;
        } else {
            return response.getCode() == 0;
        }
    }
}
