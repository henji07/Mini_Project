package com.bit.studypage.handler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;


@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        System.out.println(exception.getMessage());

        String errorMessage = getExceptionMessage(exception);
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");

        setDefaultFailureUrl("/login?error=true&errorMsg=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }

    private String getExceptionMessage(AuthenticationException exception) {
        if(exception instanceof BadCredentialsException) {
            return "로그인 정보가 틀렸습니다.";
        } else if(exception instanceof UsernameNotFoundException) {
            return "로그인 정보가 틀렸습니다.";
        } else if(exception.getMessage().contains("UserDetailsService returned null")) {
            return "로그인 정보가 틀렸습니다.";
        } else {
            return "확인되지 않은 에러";
        }
    }

}
