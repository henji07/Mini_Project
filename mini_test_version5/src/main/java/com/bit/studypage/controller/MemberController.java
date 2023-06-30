package com.bit.studypage.controller;

import java.util.*;


import com.bit.studypage.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.bit.studypage.form.MemberForm;
import com.bit.studypage.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    //회원 가입 화면으로 이동
    @GetMapping("/members/new")
    public ModelAndView joinView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/join.html");
        return mv;
    }

    //로그인 페이지 이동
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/login.html");
        return mv;
    }

    //아이디 중복 체크를 수행하는 AJAX 요청을 처리하는 핸들러 메소드
    @GetMapping("/members/checkDuplicateId/{userId}")
    public boolean checkDuplicateId(@RequestBody @PathVariable("userId") String userId) {
        //@PathVariable
        //URL 경로에서 {userId} 부분에 해당하는 값을 받아오기 위한 것
        //경로 변수를 매핑
        //클라이언트에서 전달한 경로 변수 값을 해당 파라미터로 받아올 수 있다.
        System.out.println(userId);
        return memberService.isUserIdDuplicate(userId);
    }

    //회원 정보 등록하기
    @PostMapping("/members/regist")
    public Map<String, Object> create(MemberForm form, BindingResult bindingResult) {
        //@RequestBody json을 객체로
        //@Valid 유효성 검사
        //BindingResult 오류가 담겨서 실행됨
        Map<String, Object> data = new HashMap<>();
        String result = "";

        if (bindingResult.hasErrors()) {

            System.out.println(bindingResult.hasErrors());
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            data.put("errors", errors);
            data.put("result", "fail");
            return data;
        }

        try {
            result = memberService.join(form);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        //json은 키, 밸류로 넘겨야 하기 때문에 Map으로 받아서 넘김
        //화면에 json타입으로 던져줌
        data.put("result", result);

        return data;
    }


}