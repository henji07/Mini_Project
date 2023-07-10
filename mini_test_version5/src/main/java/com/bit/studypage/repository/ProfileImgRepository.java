package com.bit.studypage.repository;

import com.bit.studypage.entity.ProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImgRepository extends JpaRepository<ProfileImg,Long> {
    ProfileImg findByUserId(Long userId);
    ProfileImg findTopByUserIdOrderByImgIdDesc(Long userId);
}
