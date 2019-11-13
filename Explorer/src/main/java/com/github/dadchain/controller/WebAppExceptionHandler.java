package com.github.dadchain.controller;

import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.util.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(basePackages = {"com.github.dad.controller"})
@Slf4j
public class WebAppExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = request.getHeader("Referer");
            if (url != null && url.indexOf('/', 10) > 0) {
                url = url.substring(0, url.indexOf('/', 10));
            }

            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", url);
            response.setHeader("Access-Control-Allow-Headers", "requested-with");
            ErrorInfo error = ErrorInfo.valueOf(e.getMessage());
            return new ResponseBean<>(error.code(), error.desc(), null);
        } catch (Exception ignore) {
            // 未知错误，一般是系统错误
            log.error(e.getMessage(), e);
            log.error("request uri " + request.getRequestURI());
            return new ResponseBean(ErrorInfo.PARAM_ERROR.code(), ErrorInfo.PARAM_ERROR.desc(), null);
        }
    }
}
