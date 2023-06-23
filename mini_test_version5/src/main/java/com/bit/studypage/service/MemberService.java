package com.bit.studypage.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bit.studypage.entity.Member;
import com.bit.studypage.form.MemberForm;
import com.bit.studypage.repository.MemberRepository;

@Mapper
public interface MemberService {
	
	
	//중복체크 - 주어진 아이디가 이미 데이터베이스에 존재하는지 확인하는 메소드 
	public boolean isUserIdDuplicate(String userId);
	
	//회원 가입
	public String join(MemberForm form);
	
	
}
