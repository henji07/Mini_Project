package com.bit.studypage.repository;

import com.bit.studypage.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
	
    Users findByUserId(String username);
    
    Optional<Users> findByEmail(String email);

    //중복 체크 - 주어진 아이디가 데이터베이스에 이미 존재하는지 확인하는 메소드
    boolean existsByUserId(String userId);
    
    
}
