package com.lingfenglong.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * FilterChain <br/>
 * Deprecated {@link FilterSecurityInterceptor} --> {@link AuthorizationFilter} <br/>
 * doFilter() --> invoke()
 * {@link ExceptionTranslationFilter} 用来处理认证过程中出现的异常<br/>
 * {@link UsernamePasswordAuthenticationFilter} 做用户名和密码的校验 attemptAuthentication()<br/>
 * {@link DelegatingFilterProxy}
 * doFilter() --> initDelegate() --> FilterChainProxy
 * {@link UserDetailsService}
 * {@link RememberMeServices}
 */


@SpringBootApplication
public class Demo1Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

}