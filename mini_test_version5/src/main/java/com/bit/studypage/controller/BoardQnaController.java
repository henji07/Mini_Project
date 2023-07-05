package com.bit.studypage.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bit.studypage.dto.BoardQnaDTO;
import com.bit.studypage.dto.CommentDTO;
import com.bit.studypage.dto.ResponseDTO;
import com.bit.studypage.entity.BoardQna;
import com.bit.studypage.service.BoardQnaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor //생성자 주입 
@RequestMapping("/board")
public class BoardQnaController {

	private final BoardQnaService boardService;
	
	
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
        List<BoardQnaDTO> boardList = boardService.getBoardList(pageNum);
        
        mv.addObject("qnaList", boardList);
        mv.addObject("currentPage", pageNum);
        mv.addObject("totalPages", boardService.getTotalPages());
        
        mv.setViewName("view/boardQna.html");

        return mv;

    }
	
	//검색 리스트로 이동 
	@GetMapping("/search/{pageNum}")
    public ModelAndView getSearchList(@RequestParam(required = false) String keyword, @PathVariable int pageNum) {
		
		ModelAndView mv = new ModelAndView();

        List<BoardQnaDTO> searchList = boardService.searchBoardsByTitle(keyword, pageNum);

        mv.addObject("searchList", searchList);
        mv.addObject("keyword", keyword);
        mv.addObject("currentPage", pageNum);
        mv.addObject("totalPages", boardService.getTotalPages());
        mv.setViewName("view/boardSearchQna.html");

        return mv;
    }
	
   
    //글 등록 -> ajax
    @PostMapping("/board-insert")
    public ResponseEntity<?> insertBoard(@RequestParam("uploadFiles") List<MultipartFile> files, BoardQnaDTO boardDTO) {
        
    	ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();

        try {
            //서비스 호출 
            boardService.insertBoard(boardDTO, files);

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
    
    //파일 로컬에 저장되어 있는 거 읽어오기 
    @Value("${file.path}")
    private String fileUploadDir;
	
	@GetMapping("/attach/{filename:.+}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String filename) throws IOException {
     System.out.println("==========================="); 
	 log.info("getImage");
	 System.out.println("filename = " + filename);
	 System.out.println("==========================="); 
    	Path file = Paths.get(fileUploadDir, filename);
    	
        InputStreamResource resource = new InputStreamResource(Files.newInputStream(file));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(file)))
                .body(resource);
    } 

    
        
    //게시 글 및 댓글 상세 조회
    @GetMapping("/board/{boardId}")
    public ModelAndView getBoard(@PathVariable long boardId) {
        ModelAndView mv = new ModelAndView();

        //게시물 조회 
        BoardQnaDTO dto = boardService.getBoardDetail(boardId);
        
        // 댓글 리스트를 가져오기.
        List<CommentDTO> comments = dto.getComments();

        mv.addObject("board", dto);
        mv.addObject("comments", comments);
        mv.setViewName("/view/boardDetailQna");

        return mv;
    }
    
    //글 수정 화면으로 이동
  	@GetMapping("/modify-board-view/{boardId}")
      public ModelAndView modifyBoardView(@PathVariable("boardId") long boardId) {
  		
  		System.out.println("boardId = " + boardId);
  		
  		ModelAndView mv = new ModelAndView();
  		
  		//게시물 조회 
        BoardQnaDTO dto = boardService.getBoardDetail(boardId);
  		
  		mv.addObject("board", dto);
  		mv.setViewName("view/boardModifyQnA.html");
  		return mv;
      }
    
    //글 수정 
    @PostMapping("/board-modify")
    public ResponseEntity<?> updateBoard(@RequestPart(value = "board", required = false) BoardQnaDTO boardDTO, 
    									 @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();
        try {
            
            boardService.updateBoard(boardDTO, files);

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
    public ResponseEntity<?> deleteBoard(BoardQnaDTO boardDTO) {
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

    
   

}
