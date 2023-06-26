package com.bit.studypage.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.studypage.dto.BoardDTO;
import com.bit.studypage.entity.Board;
import com.bit.studypage.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor //생성자 주입 
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	//글 등록 화면으로 이동
	@GetMapping("/insert-board-view")
    public String insertBoardView() {
        return "/view/insertBoard";
    }
	
   
    //글 등록 
    @PostMapping("/board-insert")
    @ResponseBody
    public Map<String, Object> insertBoard(BoardDTO boardDTO, Model model) {
    	Map<String, Object> result = new HashMap<>();
        try {
            Board board = Board.builder()
                    .boardTitle(boardDTO.getBoardTitle())
                    .boardContent(boardDTO.getBoardContent())
                    .boardWriter(boardDTO.getBoardWriter())
                    .boardRegdate(LocalDateTime.now())
                    .build();

            boardService.insertBoard(board);

            
            result.put("msg", "글이 등록되었습니다.");
            
            return result;

        } catch (Exception e) {
        	result.put("msg", e.getMessage());
            result.put("status", "error"); //에러 상황을 나타내는 추가적인 필드
        }
        
        return result;
    }
    
    //글 목록 조회
    @GetMapping("/qnaPage")
    public String boardView(Model model) {
		
		List<Board> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		
		System.out.println("boardList size: " + boardList.size()); 
		
		return "/view/boardQna";
	}	
    
    //게시 글 상세 조회
    @GetMapping("/board-detail")
	public String boardContentView(@RequestParam("boardNo") int boardNo, Model model) {
		System.out.println(boardNo);
		boardService.getBoard(boardNo);
		
       //키 - 밸류
       //"boardContentView" 내가 이름 짓는 거 
       //boardService.getBoard(boardNo) 
       //               -> 뭐를 리턴해서 모델에 넣어줄 건지 
		model.addAttribute("boardContentView", boardService.getBoard(boardNo));
		
		return "/view/";
	}



}
