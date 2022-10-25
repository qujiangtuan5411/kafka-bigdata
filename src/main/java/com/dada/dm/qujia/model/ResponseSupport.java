package com.dada.dm.qujia.model;

public class ResponseSupport {
    public static final int SUCCESS_CODE = 0;
    public static final int UNKNOWN_ERROR_CODE = -1;
    public static final String SUCCESS_MESSAGE = "success";

    public ResponseSupport() {
    }

    public static <K> Response<K> successResponse(K body) {
        return new Response<>(SUCCESS_CODE, SUCCESS_MESSAGE, body);
    }

    public static <K> Response<K> failResponse(int code, String message) {
        return new Response<>(code, message, null);
    }

    public static <K> Response<K> unknownErrorResponse(String message) {
        return new Response<>(UNKNOWN_ERROR_CODE, message, null);
    }

    public static <K> boolean isSuccess(Response<K> response) {
        if (response == null) {
            return false;
        } else {
            return response.getCode() == 0;
        }
    }
}
