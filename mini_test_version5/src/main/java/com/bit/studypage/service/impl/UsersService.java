package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private UserRepository userRepository;
    @Autowired
    public UsersService(UserRepository userRepository){
        this.userRepository = userRepository;
    };
    public Users loginUser(Long userId){
        return userRepository.findById(userId).get();
    }
}
