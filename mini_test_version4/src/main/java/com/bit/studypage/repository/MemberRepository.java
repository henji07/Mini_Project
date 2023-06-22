package com.bit.studypage.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.studypage.entity.Member;


@Repository //스프링 빈으로 등록, 스프링이 해줌
public interface MemberRepository extends JpaRepository<Member, Integer>{

	
	//중복 체크 - 주어진 아이디가 데이터베이스에 이미 존재하는지 확인하는 메소드
	boolean existsByUserId(String userId);
	
	
	

}
