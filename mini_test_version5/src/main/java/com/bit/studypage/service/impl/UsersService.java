package com.bit.studypage.service.impl;
import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UserRepository userRepository;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getUsersList() {
        return userRepository.findAll();
    }

    public Users getUsers(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Users createUsers(Users users) {
        return userRepository.save(users);
    }

    public Users updateUsers(Users users) {
        return userRepository.save(users);
    }

    public void deleteUsers(Long id) {
        userRepository.deleteById(id);
    }
}

