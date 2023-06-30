package com.bit.studypage.controller;

import com.bit.studypage.entity.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@RequestMapping(value={"/","/main"})
	public String home(@AuthenticationPrincipal Users users) {
		if(users != null)
		System.out.println(users.getUserId());

		log.info("hello");
		return "/view/home"; //이 템플릿으로 간다. 
	}
}