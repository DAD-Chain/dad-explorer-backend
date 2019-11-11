package com.github.dadchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {
    static final public String AUTH_TOKEN_HEADER = "Token";

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new HttpSessionIdResolver() {
            private CookieHttpSessionIdResolver cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
            private HeaderHttpSessionIdResolver headerHttpSessionIdResolver = new HeaderHttpSessionIdResolver(AUTH_TOKEN_HEADER);

            @Override
            public java.util.List<String> resolveSessionIds(HttpServletRequest request) {
                java.util.List<String> ls = cookieHttpSessionIdResolver.resolveSessionIds(request);
                if (null == ls || ls.size() == 0) {
                    ls = headerHttpSessionIdResolver.resolveSessionIds(request);
                }
                return ls;
            }

            @Override
            public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
                cookieHttpSessionIdResolver.setSessionId(request, response, sessionId);
                headerHttpSessionIdResolver.setSessionId(request, response, sessionId);
            }
            @Override
            public void expireSession(HttpServletRequest request, HttpServletResponse response) {
                cookieHttpSessionIdResolver.expireSession(request, response);
                headerHttpSessionIdResolver.expireSession(request, response);
            }

        };
    }
}
