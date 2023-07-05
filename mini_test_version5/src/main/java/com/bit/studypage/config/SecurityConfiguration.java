package com.bit.studypage.config;

import com.bit.studypage.handler.LoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //csrf 공격에 대한 옵션 꺼두기
                .csrf(AbstractHttpConfigurer::disable)


                //특정 URL에 대한 권한 설정
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/myPage/**", "/write/**", "/board/insert-board-view/**", "/board//modify-board-view/**")
                            // ROLE_은 붙이지 않음. hasAnyRole()을 사용할 때 자동으로 ROLE_이 붙기 때문이다.
                            .hasRole("USER");

                    authorizeRequests.requestMatchers("/admin/**")
                            // ROLE_은 붙이지 않음. hasAnyRole()을 사용할 때 자동으로 ROLE_이 붙기 때문이다.
                            .hasAnyRole("ADMIN");
                    authorizeRequests.anyRequest().permitAll();
                })
                //로그인, 로그아웃 설정
                //AuthenticationProvider에게 전달할
                //사용자 입력 아이디, 비밀번호에 대한 UsernamePasswordToken을 전달하는
                //상태까지 설정
                .formLogin(formLogin -> {
                    //권한이 필요한 요청은 해당 url로 리다이렉트
                    formLogin.loginPage("/login"); //사용자 정의 로그인페이지
                    //로그인 요청 url 매핑(가로챌 url)
                    //매핑된 url 요청이 왔을 때 시큐리티에서 알아서 인증 처리
                    formLogin.loginProcessingUrl("/loginProcess");
                    //로그인 실패 후 핸들러 등록
                    formLogin.failureHandler(loginFailureHandler);
                    formLogin.defaultSuccessUrl("/"); //로그인 성공 후 이동 페이지
                    formLogin.failureUrl("/login");//로그인 실패 후 이동 페이지
                })
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )

                .build();
    }
}
