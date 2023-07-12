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
    //아이디 찾기
    Users findByNameAndEmail(String username, String email);

    //비밀번호찾기
    Users findByUserIdAndEmail(String userId, String email);

    //중복 체크 - 주어진 아이디가 데이터베이스에 이미 존재하는지 확인하는 메소드

    //이메일 중복체크
    boolean existsByEmail(String email);

    //전화번호 중복체크
    boolean existsByPhone(String phone);
}
