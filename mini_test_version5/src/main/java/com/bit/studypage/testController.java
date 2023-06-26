package com.bit.studypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

	@GetMapping("qnaPage")
	public String test() {
		
		return "view/boardQna";
	}
	
	@GetMapping("studyPage")
	public String test2() {
		
		return "view/boardTogether";
	}
	
	@GetMapping("freePage")
	public String test3() {
		
		return "view/boardCommunicate";
	}
	
	@GetMapping("certificatePage")
	public String test4() {
		
		return "view/boardCertificate";
	}
	
	@GetMapping("fullSearch")
	public String test5() {
		
		return "view/fullSearch";
	}
	
	@GetMapping("login")
	public String test6() {
		
		return "view/login";
	}
	
	@GetMapping("login/findId")
	public String test7() {
		
		return "view/findId";
	}
	
	@GetMapping("login/findPw")
	public String test8() {
		
		return "view/findPw";
	}
	
	@GetMapping("myPage")
	public String test9() {
		
		return "view/myPage";
	}
	
	@GetMapping("write")
	public String test10() {
		
		return "view/write";
	}
	
	@GetMapping("naver-join")
	public String test11() {
		
		return "view/naverJoin";
	}
	
	@GetMapping("kakao-join")
	public String test12() {
		
		return "view/kakaoJoin";
	}
	
	@GetMapping("google-join")
	public String test13() {
		
		return "view/googleJoin";
	}
}
