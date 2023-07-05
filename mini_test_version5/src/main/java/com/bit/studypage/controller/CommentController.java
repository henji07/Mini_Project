package com.bit.studypage.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bit.studypage.dto.CommentDTO;
import com.bit.studypage.dto.ResponseCommentDTO;
import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.MemberRepository;
import com.bit.studypage.service.impl.CommentServiceImpl;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {

	 private final CommentServiceImpl commentService;
	 private final MemberRepository memberRepository;
	 
	// 댓글 작성
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/comments-insert/{boardId}")
    public ResponseCommentDTO<?> writeComment(@PathVariable("boardId") Long boardId, @RequestBody CommentDTO commentDto) {
        // 원래 로그인을 하면, User 정보는 세션을 통해서 구하고 주면 되지만,
        // 로그인은 생략하고, 임의로 findById 로 유저 정보를 넣어줌.
        // 추후에 로그인 기능을 도입하고 유저 정보는 세션을 통해서 넣어주면 됨.
    	Users user = memberRepository.findById((long) 2).orElseThrow(() -> new NoSuchElementException("No User found with id 1"));
        
        System.out.println("댓글 작성 요청 - boardId: " + boardId);
        System.out.println("댓글 내용: " + commentDto.getContent());
        System.out.println("댓글 작성자: " + commentDto.getWriter());
        System.out.println("================================================"); 
        
        return new ResponseCommentDTO<>("성공", "댓글 작성을 완료했습니다.", commentService.writeComment(boardId, commentDto, user), null);
    }
    
    // 게시글에 달린 댓글 모두 불러오기
    @ApiOperation(value = "댓글 불러오기", notes = "게시글에 달린 댓글을 모두 불러온다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comments/{boardId}")
    public ResponseCommentDTO<?> getComments(@PathVariable("boardId") Long boardId) {
    	List<CommentDTO> comments = commentService.getComments(boardId);
    	
    	System.out.println("댓글 개수: " + comments.size()); // 댓글 개수 출력
    	
    	System.out.println("================================================"); 

        return new ResponseCommentDTO<>("성공", "댓글을 불러왔습니다.",comments, null);
        
        
        
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
