package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Users;

import com.bit.studypage.repository.UsersRepository;
import com.bit.studypage.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    @Override
    public Users loginUser(Long usersId){
        return usersRepository.findById(usersId).get();
    }

    @Override
    public void updateUesr(Users user) {
        usersRepository.save(user);
    }

    @Override
    public void delUser(Users users) {
        usersRepository.delete(users);
    }

//    @Override
//    public String getInterest(Long userId) {
//
//    }
}
