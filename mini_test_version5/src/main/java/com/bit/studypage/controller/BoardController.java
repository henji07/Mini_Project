package com.bit.studypage.controller;

<<<<<<< HEAD
import com.bit.studypage.dto.BoardDto;
import com.bit.studypage.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<BoardDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("postList", boardDtoList);
        return "board/list.html";
    }

    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/freePage";
    }

    @DeleteMapping("/delete-page")
    public String deletePage() {
        return "board/post.html"; // 삭제하면 다시 가야하지 않을까?
    }
=======
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bit.studypage.dto.BoardDTO;
import com.bit.studypage.dto.ResponseDTO;
import com.bit.studypage.entity.Board;
import com.bit.studypage.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor //생성자 주입 
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	//글 등록 화면으로 이동
	@GetMapping("/insert-board-view")
    public ModelAndView insertBoardView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view/boardInsertQna.html");
		return mv;
    }
	
	//글 목록 화면으로 이동 
	@GetMapping("/qnaPage/{pageNum}")
    public ModelAndView getBoardList(@PathVariable int pageNum) {

        ModelAndView mv = new ModelAndView();

        //게시글 목록을 가져옴
        List<BoardDTO> boardList = boardService.getBoardList(pageNum);
        
        mv.addObject("qnaList", boardList);
        mv.addObject("currentPage", pageNum);
        mv.addObject("totalPages", boardService.getTotalPages());
        
        mv.setViewName("view/boardQna.html");

        return mv;

    }
	
   
    //글 등록 -> ajax
    @PostMapping("/board-insert")
    public ResponseEntity<?> insertBoard(BoardDTO boardDTO) {
        
    	ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();

        try {
            
            boardService.insertBoard(boardDTO);

            Map<String, String> returnMap = new HashMap<String, String>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);

        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
        
    //게시 글 상세 조회
    @GetMapping("/board/{boardId}")
    public ModelAndView getBoard(@PathVariable long boardId) {
        ModelAndView mv = new ModelAndView();

        BoardDTO dto = boardService.getBoardDetail(boardId);

        mv.addObject("board", dto);
        mv.setViewName("/view/boardDetailQna");

        return mv;
    }
    
    //글 수정 
    @PutMapping("/board")
    public ResponseEntity<?> updateBoard(BoardDTO boardDTO) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();
        try {
            //빌더로 엔티티 형태로 만들어주기
          //  Board board = boardDTO.DTOToEntity();
            boardService.updateBoard(boardDTO);

            //리턴해줄 맵
            Map<String, String> returnMap = new HashMap<String, String>();

            returnMap.put("msg", "정상적으로 수정되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);

        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    
    //글 삭제 
    @DeleteMapping ("/board")
    public ResponseEntity<?> deleteBoard(BoardDTO boardDTO) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();
        try {
            //Long으로 보내기 때문에 엔티티 안 만들어도 됨. 
            boardService.deleteBoard(boardDTO.getBoardId());

            Map<String, String> returnMap = new HashMap<String, String>();

            returnMap.put("msg", "정상적으로 삭제되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    


>>>>>>> 0066a277d01a6f5ab3807de17468cffb87ad526d
}
