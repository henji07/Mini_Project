package com.bit.studypage.service.impl;

import com.bit.studypage.entity.ProfileImg;
import com.bit.studypage.repository.ProfileImgRepository;
import com.bit.studypage.service.ProfileImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileImgServiceImpl implements ProfileImgService {
    ProfileImgRepository profileImgRepository;
    @Autowired
    ProfileImgServiceImpl(ProfileImgRepository profileImgRepository){
        this.profileImgRepository=profileImgRepository;
    }
    @Override
    public void updateProfileImg(ProfileImg profileImg){
        profileImgRepository.save(profileImg);
    }

    @Override
    public ProfileImg getProfileImg(Long userId) {
        return profileImgRepository.findTopByUserIdOrderByImgIdDesc(userId);
    }
}
