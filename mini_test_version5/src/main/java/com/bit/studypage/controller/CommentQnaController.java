package com.bit.studypage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bit.studypage.dto.BoardCmmntQnaDTO;
import com.bit.studypage.dto.CommentQnaDTO;
import com.bit.studypage.dto.ResponseCommentDTO;
import com.bit.studypage.service.CommentQnaService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentQnaController {

	// CommentQnaService 객체 주입
	private final CommentQnaService commentService;
	 
	// 댓글 작성을 위한 API를 정의. HTTP 메소드 POST.  
	// PathVariable로 boardId를 받아와, 해당 게시글에 댓글을 달 수 있도록 함.
	// Authentication 객체를 받아와 사용자의 인증 정보를 확인
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/comments-insert/{boardId}")
    public ResponseCommentDTO<?> writeComment(@PathVariable("boardId") Long boardId, @RequestBody CommentQnaDTO commentDto, 
    										  Authentication authentication) {
        
      //  System.out.println("댓글 작성 요청 - boardId: " + boardId);
        
        // 댓글 작성을 위해 Service 레이어의 메소드를 호출하고, 그 결과를 반환
        return new ResponseCommentDTO<>("성공", "댓글 작성을 완료했습니다.", commentService.writeComment(boardId, commentDto, authentication), null);
    }
    
	// 게시글에 달린 댓글들을 불러오는 API를 정의. HTTP 메소드는 GET.
	// PathVariable로 boardId를 받아와 해당 게시글의 댓글을 모두 불러옴.
    @ApiOperation(value = "댓글 불러오기", notes = "게시글에 달린 댓글을 모두 불러온다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comments/{boardId}")
    public ResponseCommentDTO<?> getComments(@PathVariable("boardId") Long boardId) {
    	// Service 레이어의 메소드를 호출하여 해당 게시글의 댓글들을 가져옴
    	List<CommentQnaDTO> comments = commentService.getComments(boardId);
    	
    //	System.out.println("댓글 개수: " + comments.size()); // 댓글 개수 출력

    	// 댓글을 불러온 결과를 반환
        return new ResponseCommentDTO<>("성공", "댓글을 불러왔습니다.",comments, null);     
        
    }
    
    
    @PostMapping("/comments/refresh")
    @ResponseBody
    public List<BoardCmmntQnaDTO> getBoardQnaCommnetList(@RequestBody Map<String,Object> data){
    	
    	List<BoardCmmntQnaDTO> dataList = null;
    	try {
    		String boardIdStr = (String)data.get("boardId");
    		long boardId = Long.parseLong(boardIdStr);
    	
    		dataList = commentService.getBoardQnaCommnetList(boardId);
    	}
    	catch(Exception e) {
    		log.error(e.getMessage());
    	}
    	
    	return dataList;
    }
    
    
    // 댓글 삭제
    @ApiOperation(value = "댓글 삭제", notes = "게시글에 달린 댓글을 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/comments/{boardId}/{commentId}")
    public ResponseCommentDTO deleteComment(@PathVariable("boardId") Integer boardId, @PathVariable("commentId") Integer commentId) {
        // 추후 JWT 로그인 기능을 추가하고나서, 세션에 로그인된 유저와 댓글 작성자를 비교해서, 맞으면 삭제 진행하고
        // 틀리다면 예외처리를 해주면 된다.

        return new ResponseCommentDTO("성공", "댓글 삭제 완료", commentService.deleteComment(commentId), null);
    }
}
