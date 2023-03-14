package com.dada.dm.qujia.exception;

import com.dada.dm.qujia.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @description 业务异常处理
 * @className BaseExceptionHandler
 * @packageName com.dada.dm.qujia.exception
 * @author jt.Qu
 * @date 2023/3/14 10:39
 */
@ControllerAdvice
@Slf4j
public class BaseExceptionHandler {


    /**
     * 业务处理异常 DwException
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<String> handleException(HttpServletRequest request, Exception e) {
        String message = "request exception, path: " +
                request.getServletPath();
        log.warn(message, e);

        Response<String> response;

        if (e instanceof DwException) {
            DwException dwException = (DwException) e;
            if (dwException.getResponseCodeEnum() == null) {
                return Response.fail(dwException.getMessage());
            }
            response = new Response(dwException.getResponseCodeEnum());
        } else {
            response = new Response(ResponseCodeEnum.FAIL);
        }

        return response;
    }

}
