package com.bit.studypage.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bit.studypage.dto.MemberDTO;
import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.UsersRepository;
import com.bit.studypage.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    //중복체크 - 주어진 아이디가 이미 데이터베이스에 존재하는지 확인하는 메소드
    @Transactional(readOnly = true)
    public boolean isUserIdDuplicate(String userId) {
        return usersRepository.existsByUserId(userId);
    }

    //회원 가입
    public String join(MemberDTO member) {
        //성공할 때만 success로 나머지는 fail
        String result = "fail";

        //배열을 스트링으로 바꿔주는
        //DB에 배열이 들어갈 수 없기 때문에
        //배열 넣으려면 테이블 따로 만들어야 함
        //중간에 쉼표 넣어서 DB에 넣어주는 거
        StringBuffer sb = new StringBuffer();

        String[] arr = member.getInterest();
        if (arr != null && arr.length > 0) {
            for (String str : arr) {
                if (sb.length() > 0) sb.append(",");
                sb.append(str);
            }
        }

        //Map에 값을 설정해서 엔티티에 빌드로 넘기기 위해
        Map<String, Object> formData = new HashMap<>();
        formData.put("userId", member.getUserId());
        formData.put("name", member.getName());
        formData.put("password", passwordEncoder.encode(member.getPassword()));
        formData.put("email", member.getEmail());
        formData.put("phone", member.getPhone());
        formData.put("interest", sb.toString());
        formData.put("gender", member.getGender());

        // Users 객체를 생성하여 DB에 저장
        Users user = Users.builder()
                .userId((String) formData.get("userId"))
                .name((String) formData.get("name"))
                .password((String) formData.get("password"))
                .email((String) formData.get("email"))
                .phone((String) formData.get("phone"))
                .interest((String) formData.get("interest"))
                .gender((String) formData.get("gender"))
                .roles(new ArrayList<>(Collections.singleton("ROLE_USER")))
                .build();


        //Map으로 넘긴 데이터를 빌더를 통해 DB에 저장하는 방법
        //Member.builder().data(formData).build() -> 엔티티를 만들어서 save로 레파지토리에 던짐
        //그 결과를 memberResult 받는다. 멤버 일련 번호가 있다.
        Users memberResult = usersRepository.save(user);

        //값이 0보다 크면 success
        if (ObjectUtils.isNotEmpty(memberResult)) {
            Long memberSno = memberResult.getUsersId();

            if(memberSno > 0) {
                //등록 성공
                result = "success";
            }
        }
        return result;
    }

    @Override
    public Users save(Users users) {
        return null;
    }

    @Override
    public Users findOne(Long id) {
        return null;
    }

    @Override
    public boolean isEmailDuplicate(String email) {
        return false;
    }

    @Override
    public boolean isPhoneDuplicate(String phone) {
        return false;
    }
    

}