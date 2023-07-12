package com.bit.studypage.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bit.studypage.dto.board.BoardCmmntQnaDTO;
import com.bit.studypage.dto.board.CommentQnaDTO;
import com.bit.studypage.dto.board.ResponseCommentDTO;
import com.bit.studypage.entity.Users;
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
    
    
    /*댓글 목록 가져오기 */
    @PostMapping("/comments/refresh")
    @ResponseBody
    public Map<String, Object> getBoardQnaCommnetList(@RequestBody Map<String,Object> data, Authentication authentication){
    	
    	//사용자 정보 넘겨주기 
    	String userId = null;
        
        if(ObjectUtils.isNotEmpty(authentication)) {
	        if (authentication.getPrincipal() instanceof Users) {
	            Users user = (Users) authentication.getPrincipal();
	            userId = user.getUserId();
	        }
        }
    	
    	List<BoardCmmntQnaDTO> dataList = null;
    	try {
    		String boardIdStr = (String)data.get("boardId");
    		long boardId = Long.parseLong(boardIdStr);
    	
    		dataList = commentService.getBoardQnaCommnetList(boardId);
    	}
    	catch(Exception e) {
    		log.error(e.getMessage());
    	}
    	
    	 Map<String, Object> result = new HashMap<>();
    	    result.put("currentUserId", userId);
    	    result.put("comments", dataList);
    	    
    	    return result;
    }
    
    /* 댓글 삭제 */
    @PostMapping("/comments/delete")
    @ResponseBody
    public String deleteBoardQnaComment(@RequestBody Map<String, Object> data) {
    		System.out.println("코멘트아이디"+data);
        try {
            //String commentIdStr = Integer.toString((Integer) data.get("commentId"));
            int commentId = (Integer) data.get("commentId");
            
            System.out.println("코멘트아이디"+commentId);
            
            return commentService.deleteComment(commentId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "댓글 삭제 과정에서 오류가 발생했습니다.";
        }
    }        
    
    /* 댓글 수정 */
    @PostMapping("/comments/modify")
    @ResponseBody
    public String modifyBoardQnaComment(@RequestBody Map<String, Object> data) {
    	try {
            int commentId = (Integer) data.get("commentId");
            String newContent = (String) data.get("newContent");
            
            System.out.println("수정 댓글=" + commentId + newContent);
            
            return commentService.modifyComment(commentId, newContent);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "댓글 수정 과정에서 오류가 발생했습니다.";
        }
    } 
    
}
