package com.bit.studypage.service;

import com.bit.studypage.entity.ProfileImg;

public interface ProfileImgService {
    public void updateProfileImg(ProfileImg profileImg);
    public ProfileImg getProfileImg(Long userId);
}
