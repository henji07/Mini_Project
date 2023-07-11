package com.bit.studypage.service;

import org.apache.ibatis.annotations.Mapper;

import com.bit.studypage.entity.Users;
import com.bit.studypage.dto.MemberDTO;

@Mapper
public interface MemberService {
	
	
	//중복체크 - 주어진 아이디가 이미 데이터베이스에 존재하는지 확인하는 메소드 
	public boolean isUserIdDuplicate(String userId);
	
	//회원 가입
	public String join(MemberDTO form);
	
	//회원 정보 저장 
	public Users save(Users users);
	
	//회원 정보 조회 
    public Users findOne(Long id);

    //이메일 중복체크
	public boolean isEmailDuplicate(String email);

	//전화번호 중복체크 
	public boolean isPhoneDuplicate(String phone);

	
}
