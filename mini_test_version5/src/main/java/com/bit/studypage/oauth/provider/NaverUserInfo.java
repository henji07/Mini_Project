package com.bit.studypage.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

    Map<String, Object> attributes;

    Map<String, Object> properties;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties = (Map<String, Object>) attributes.get("response");
    }



    @Override
    public String getProvierId() {
        return this.properties.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return this.properties.get("email").toString();
    }

    @Override
    public String getName() {
        return this.properties.get("name").toString();
    }
}
