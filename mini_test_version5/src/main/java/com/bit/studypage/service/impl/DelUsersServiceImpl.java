package com.bit.studypage.service.impl;

import com.bit.studypage.entity.DelUsers;
import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.DelUserRepository;
import com.bit.studypage.service.DelUsersService;
import groovy.transform.AutoImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelUsersServiceImpl implements DelUsersService {
    private DelUserRepository delUserRepository;
    @Autowired
    DelUsersServiceImpl(DelUserRepository delUserRepository){
        this.delUserRepository = delUserRepository;
    }
    @Override
    public void setDelUser(DelUsers user) {
        delUserRepository.save(user);
    }
}
