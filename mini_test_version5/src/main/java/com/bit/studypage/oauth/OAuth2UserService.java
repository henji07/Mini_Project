package com.bit.studypage.oauth;

import com.bit.studypage.entity.Users;
import com.bit.studypage.oauth.provider.GoogleUserInfo;
import com.bit.studypage.oauth.provider.KakaoUserInfo;
import com.bit.studypage.oauth.provider.NaverUserInfo;
import com.bit.studypage.oauth.provider.OAuth2UserInfo;
import com.bit.studypage.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class OAuth2UserService extends DefaultOAuth2UserService {


    private PasswordEncoder passwordEncoder;

    private UsersRepository usersRepository;

    @Autowired
    public OAuth2UserService(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("loadUser 메소드 호출~~~!!!!!!");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String userName = "";
        String providerId = "";

        OAuth2UserInfo oAuth2UserInfo = null;

        //소셜 카테고리 검증
        if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("skldljkldjklzzzzzzzzzzzzzzzzzzzzzzz9zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

            userName = oAuth2UserInfo.getName();
            providerId = oAuth2UserInfo.getProvierId();
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());

            userName = oAuth2UserInfo.getName();
            providerId = oAuth2UserInfo.getProvierId();
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

            userName = oAuth2UserInfo.getName();
            providerId = oAuth2UserInfo.getProvierId();
        }

        String provider = oAuth2UserInfo.getProvider();
        String userId = provider + "_" + providerId;
        String password = passwordEncoder.encode(oAuth2UserInfo.getName());
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        //소셜 로그인 기록이 있는지 검사할 객체
        Users user;

        if(usersRepository.findByUserId(userId) != null) {
            //이미 소셜로그인한 기록이 있으면
            //정보를 users엔티티에 담아준다.
            user = usersRepository.findByUserId(userId);
        } else {
            user = null;
        }

        //소셜 로그인 기록이 없으면 회원가입 처리
        if(user == null) {
            user = Users.builder()
                    .userId(userId)
                    .password(password)
                    .name(userName)
                    .email(email)
                    .roles(Collections.singletonList(role))
                    .build();

            usersRepository.save(user);
        }

        //Security Context에 인증 정보 저장
        return Users.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .attributes(oAuth2User.getAttributes())
                .build();
    }

}
