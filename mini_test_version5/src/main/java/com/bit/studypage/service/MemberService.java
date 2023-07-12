package com.bit.studypage.service;

<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

=======
import org.apache.ibatis.annotations.Mapper;
import com.bit.studypage.entity.Users;
import com.bit.studypage.dto.MemberDTO;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
>>>>>>> origin/newyoojin
import com.bit.studypage.entity.Member;
import com.bit.studypage.form.MemberForm;
import com.bit.studypage.repository.MemberRepository;

@Mapper
public interface MemberService {
<<<<<<< HEAD
	
	
	//중복체크 - 주어진 아이디가 이미 데이터베이스에 존재하는지 확인하는 메소드 
	public boolean isUserIdDuplicate(String userId);
	
	//회원 가입
	public String join(MemberForm form);
	
	
=======


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

	//아이디 찾기
	public String findId(String username, String email);

	//비밀번호 찾기
	public boolean findPassword(String userId, String email);

	//임시 비밀번호 체크
	public boolean checkTempPassword(String username, String tempPassword);

	//임시 비밀번호 검증 후 비밀번호 변경
	public void changePassword(String email, String newPassword);
>>>>>>> origin/newyoojin
}
