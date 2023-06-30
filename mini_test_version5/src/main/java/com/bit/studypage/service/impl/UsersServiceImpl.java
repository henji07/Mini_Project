package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.UserRepository;
import com.bit.studypage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public UsersServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    };
    @Override
    public Users loginUser(Long usersId){
        return userRepository.findById(usersId).get();
    }

    @Override
    public void updateUesr(Users user) {
        userRepository.save(user);
    }

    @Override
    public void delUser(Users users) {
        userRepository.delete(users);
    }

//    @Override
//    public String getInterest(Long userId) {
//
//    }
}
