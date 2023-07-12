package com.bit.studypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

<<<<<<< HEAD
	@GetMapping("qnaPage")
	public String test() {
		
		return "view/boardQna";
	}
	
=======
//	@GetMapping("qnaPage")
//	public String test() {
//
//		return "view/boardQna";
//	}

>>>>>>> origin/newyoojin
	@GetMapping("studyPage")
	public String test2() {
		
		return "view/boardTogether";
	}
	
	@GetMapping("freePage")
	public String test3() {
		
		return "view/boardCommunicate";
	}
<<<<<<< HEAD
	
	@GetMapping("certificatePage")
	public String test4() {
		
		return "view/boardCertificate";
	}
=======
//	@GetMapping("certificatePage")
//	public String test4() {
//
//		return "view/boardCertificate";
//	}

//	@GetMapping("fullSearch")
//	public String test5() {
//
//		return "view/fullSearch";
//	}

>>>>>>> origin/newyoojin
//
//	@GetMapping("fullSearch")
//	public String test5() {
//
//		return "view/fullSearch";
//	}
	
<<<<<<< HEAD
	@GetMapping("login")
	public String test6() {
		
		return "view/login";
	}
=======
//	@GetMapping("login")
//	public String test6() {
//
//		return "view/login";
//	}
>>>>>>> origin/newyoojin
	
	@GetMapping("login/findId")
	public String test7() {
		
		return "view/findId";
	}
	
	@GetMapping("login/findPw")
	public String test8() {
		
		return "view/findPw";
	}
	
<<<<<<< HEAD
	@GetMapping("myPage")
	public String test9() {
		
		return "view/myPage";
	}
=======
//	@GetMapping("myPage")
//	public String test9() {
//
//		return "view/myPage";
//	}

//	@GetMapping("myPage")
//	public String test9() {
//
//		return "view/myPage";
//	}

>>>>>>> origin/newyoojin
	
	@GetMapping("write")
	public String test10() {
		
		return "view/write";
	}
<<<<<<< HEAD
	
=======

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

>>>>>>> origin/newyoojin
}
