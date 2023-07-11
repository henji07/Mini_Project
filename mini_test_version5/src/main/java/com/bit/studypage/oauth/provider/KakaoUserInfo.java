package com.bit.studypage.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{
    //자원 서버(카카오)에서 제공한 원시 데이터 형식
    /*
     * {
     *   kakao_account: {
     *       profile: {
     *           nickname: ,
     *       },
     *       email: ,
     *   }
     * }
     * */

    Map<String, Object> attributes;

    Map<String, Object> properties;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties = (Map<String, Object>)attributes.get("kakao_account");
    }


    @Override
    public String getProvierId() {
        return this.attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return this.properties.get("email").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> profile = (Map<String, Object>) properties.get("profile");
        return profile.get("nickname").toString();
    }
}
