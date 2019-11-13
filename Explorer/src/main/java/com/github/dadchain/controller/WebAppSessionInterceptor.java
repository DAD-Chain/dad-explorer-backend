package com.github.dadchain.controller;

import com.github.dadchain.util.ErrorInfo;
import com.github.dadchain.config.HttpSessionConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Slf4j
@Component
public class WebAppSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            HttpSession session = request.getSession(false);

            if (method.isAnnotationPresent(NeedLogin.class)) {
                if (session == null
                        || StringUtils.isEmpty((String) session.getAttribute("uid"))) {
                    throw new RuntimeException(ErrorInfo.USER_NOT_LOGGED_IN.name());
                }
            }
            if(null != session){
                response.setHeader(HttpSessionConfig.AUTH_TOKEN_HEADER, session.getId());
            }

            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
