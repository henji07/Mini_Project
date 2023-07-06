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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.bit.studypage.dto.CommentQnaDTO;
import com.bit.studypage.dto.ResponseDTO;
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
	
	//검색 결과 화면으로 이동 
	//required = false로 설정되면 해당 파라미터가 필수가 아니라는 것
	//파라미터가 누락되더라도 예외가 발생하지 않고 기본값으로 null이 할당
	@GetMapping("/search/{pageNum}")
    public ModelAndView getSearchList(@RequestParam(required = false) String keyword, @PathVariable int pageNum) {
		
		ModelAndView mv = new ModelAndView();

        List<BoardQnaDTO> searchList = boardService.searchBoardsByTitle(keyword, pageNum);

        mv.addObject("searchList", searchList);
        mv.addObject("keyword", keyword);
        mv.addObject("currentPage", pageNum);
        mv.addObject("totalPages", boardService.getSearchTotalPages(keyword));
        
        if (searchList.isEmpty()) {
            mv.addObject("noResultMessage", "검색 결과가 없습니다.");
        }
        
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
    
    //서버에 저장되어 있는 거 읽어오기 
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
    public ModelAndView getBoard(@PathVariable long boardId, Authentication authentication) {
        ModelAndView mv = new ModelAndView();

        //게시물 조회 
        BoardQnaDTO dto = boardService.getBoardDetail(boardId);
        
        // 댓글 리스트를 가져오기.
        List<CommentQnaDTO> comments = dto.getComments();

        mv.addObject("board", dto);
        mv.addObject("comments", comments);
        
        // 로그인한 사용자 정보 전달
        // 사용자가 로그인한 상태인지 확인
        if (authentication != null && authentication.isAuthenticated()) {
        	//getName() 메서드는 Authentication 인터페이스에서 정의된 메서드로, 
        	//실제 구현은 Principal 객체의 getName() 메서드를 호출하여 사용자의 이름을 가져옴
        	//로그인한 사용자 정보를 가져와서 username이라는 변수에 저장
            String username = authentication.getName();
            mv.addObject("username", username);
        }
        
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
        	// 서비스에서 게시글 수정 로직 실행
            boardService.updateBoard(boardDTO, files);

            // 클라이언트에게 전달할 응답 메시지 맵 생성
            Map<String, String> returnMap = new HashMap<String, String>();

            // 수정이 정상적으로 되었음을 알리는 메시지
            returnMap.put("msg", "정상적으로 수정되었습니다.");

            // 응답 DTO에 메시지 맵을 설정
            responseDTO.setItem(returnMap);

            // 정상적인 응답 반환
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
