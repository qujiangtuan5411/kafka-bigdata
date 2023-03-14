package com.dada.dm.qujia.exception;

import lombok.Data;

/**
 * @author jt.Qu
 * @description DwGridException统一异常模块 -- RuntimeException
 */
@Data
public class DwException extends AbstractCodeException {

    private static final long serialVersionUID = 7805907487081899539L;

    /**
     * 枚举
     */
    private ResponseCodeEnum responseCodeEnum;

    /**
     * 生成业务异常
     *
     * @param responseCodeEnum 枚举
     */
    public DwException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getCode(), responseCodeEnum.getMsg());
        this.responseCodeEnum = responseCodeEnum;
    }

    public DwException(Integer code, String message) {
        super(code, message);
    }

    public DwException(String message) {
        super(ResponseCodeEnum.FAIL.getCode(), message);
    }
}
