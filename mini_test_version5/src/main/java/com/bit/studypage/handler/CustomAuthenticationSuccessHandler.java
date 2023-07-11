//package com.bit.studypage.handler;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        // 로그인 성공 후 세션에 원하는 정보를 저장할 수 있습니다.
//        HttpSession session = request.getSession();
//        session.setAttribute("myAttribute", "myValue");
//
//        // 다음 URL로 리다이렉트 합니다.
//        response.sendRedirect("/");
//    }
//}
