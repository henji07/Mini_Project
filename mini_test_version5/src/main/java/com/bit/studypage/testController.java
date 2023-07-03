package com.bit.studypage;

import com.bit.studypage.domain.entity.Board;
import com.bit.studypage.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class testController {
	BoardService boardService;

<<<<<<< HEAD
	@Autowired
	public testController(BoardService boardService) {
		this.boardService = boardService;
	}
	@GetMapping("qnaPage")
	public String test() {
		
		return "view/boardQna";
	}
=======
>>>>>>> 0066a277d01a6f5ab3807de17468cffb87ad526d
	
	@GetMapping("studyPage")
	public String test2() {
		
		return "view/boardTogether";
	}
	
	@GetMapping("freePage")
	public String test3(Model model) {
		//게시글 목록 조회
		List<Board> boardList = new ArrayList<>();

		boardList = boardService.findBoardList();
		System.out.println("1111111111111111111111111111111111111111");
		for(Board board : boardList) {
			System.out.println(board);
		}

		//model 에다가 목록 담기
		model.addAttribute("boardList", boardList);
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
