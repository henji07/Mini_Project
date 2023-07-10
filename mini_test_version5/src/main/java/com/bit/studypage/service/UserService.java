package com.bit.studypage.service;

import com.bit.studypage.entity.Likes;
import com.bit.studypage.entity.Users;
import org.springframework.data.domain.Page;

public interface UserService {
    Users loginUser(Long userId);
    void updateUesr(Users user);
//    String getInterest(Long userId);
    void delUser(Users users);

}
