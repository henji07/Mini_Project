package com.bit.studypage.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.studypage.entity.Users;


@Repository //스프링 빈으로 등록, 스프링이 해줌
public interface MemberRepository extends JpaRepository<Users, Long>{

	
	//중복 체크 - 주어진 아이디가 데이터베이스에 이미 존재하는지 확인하는 메소드
	boolean existsByUserId(String userId);

	//이메일 중복체크
	boolean existsByEmail(String email);

	//전화번호 중복체크 
	boolean existsByPhone(String phone);

	//userId 찾기 - 댓글
	Optional<Users> findByUserId(String userId);	

}
