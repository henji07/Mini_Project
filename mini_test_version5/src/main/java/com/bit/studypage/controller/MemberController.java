package com.bit.studypage.controller;

import java.util.*;


import com.bit.studypage.dto.ResponseDTO;
import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.bit.studypage.dto.MemberDTO;
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

    //임시 비밀번호 체크 페이지 이동
    @GetMapping("/temp-password-check")
    public ModelAndView tempPasswordView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/tempPasswordCheck.html");
        return mv;
    }

    //비밀번호 변경 페이지 이동
    @GetMapping("/password-change")
    public ModelAndView passwordChangeView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/passwordChange.html");
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
    @ResponseBody //json으로 바꿈
    public Map<String, Object> create(@RequestBody @Valid MemberDTO form, BindingResult bindingResult) {
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

        try{
            result = memberService.join(form);
        }
        catch(Exception e) {
            log.error(e.getMessage(), e);
        }

        //json은 키, 밸류로 넘겨야 하기 때문에 Map으로 받아서 넘김
        //화면에 json타입으로 던져줌
        data.put("result", result);

        return data;
    }


    //아이디 찾기
    @GetMapping("/login/find-id")
    public ResponseEntity<?> findId2(@RequestParam String username, @RequestParam String email) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        String userId;
        try {
            userId = memberService.findId(username, email);

            Map<String, String> returnMap = new HashMap<>();

            if(userId != null && !userId.equals("")) {
                returnMap.put("id", userId);
            } else {
                returnMap.put("id", "NotExist");
            }

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    //비밀번호 찾기
    @PostMapping("/login/find-pw")
    public ResponseEntity<?> findPassword(@RequestParam String userId, @RequestParam String email) {
        try {
            System.out.println(memberService.findPassword(userId, email));
            if (memberService.findPassword(userId, email)) {
                //임시 비밀번호 생성 후 이메일로 전송 완료 시 메시지 반환
                return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다. 메일을 확인해 주세요.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("임시 비밀번호 생성 및 이메일 전송 실패");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 찾기 요청이 실패하였습니다.");
        }
    }

    //임시 비밀번호 검증
    @PostMapping("/temp-password/check")
    public ResponseEntity<?> checkTempPassword(@RequestParam("userId") String userId, @RequestParam("tempPassword") String tempPassword) {
        boolean isMatch = memberService.checkTempPassword(userId, tempPassword);

        if (isMatch) {
            // 임시 비밀번호가 일치하면 응답 메시지 클라이언트에게 전달
            return ResponseEntity.ok("임시 비밀번호가 일치합니다.");
        } else {
            // 임시 비밀번호가 일치하지 않으면 다시 임시 비밀번호 입력
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력한 비밀번호가 일치하지 않습니다.");
        }
    }

    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@RequestParam("userId") String userId, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
        if (newPassword.equals(confirmPassword)) {
            memberService.changePassword(userId, newPassword);
            return ResponseEntity.ok("비밀번호 변경에 성공하였습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력한 비밀번호가 일치하지 않습니다.");
        }
    }

}

