package com.bit.studypage.controller;

import com.bit.studypage.repository.UserRepository;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class MyPageController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/myPageView")
    public ModelAndView viewMyPage(){
        log.info("용순");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/myPage.html");
        mv.addObject("user",userRepository.findById(1L).get());
        return mv;
    }
}
