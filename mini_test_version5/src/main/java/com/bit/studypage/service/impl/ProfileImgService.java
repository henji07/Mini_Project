package com.bit.studypage.service.impl;

import com.bit.studypage.entity.ProfileImg;
import com.bit.studypage.repository.ProfileImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileImgService {
    ProfileImgRepository profileImgRepository;
    @Autowired
    ProfileImgService(ProfileImgRepository profileImgRepository){
        this.profileImgRepository=profileImgRepository;
    }
    public void updateProfileImg(ProfileImg profileImg){
        profileImgRepository.save(profileImg);
    }
}
