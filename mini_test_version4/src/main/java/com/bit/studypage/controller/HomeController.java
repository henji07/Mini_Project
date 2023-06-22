package com.bit.studypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@RequestMapping(value={"/","/main"})
	public String home() {
		
		log.debug("hello");
		return "/view/home"; //이 템플릿으로 간다. 
	}
}