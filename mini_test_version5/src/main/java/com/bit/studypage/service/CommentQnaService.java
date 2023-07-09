package com.bit.studypage.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.bit.studypage.dto.BoardCmmntQnaDTO;
import com.bit.studypage.dto.CommentQnaDTO;
import com.bit.studypage.entity.Users;

public interface CommentQnaService {
	
	//사용자의 인증 정보 확인
	public Users validateAuthentication(Authentication authentication);
	
	// 댓글 작성 
	public CommentQnaDTO writeComment(long boardId, CommentQnaDTO commentDto, Authentication authentication);
    
    //댓글 삭제 
	public String deleteComment(int commentId);
	
	//댓글 목록 
	public List<BoardCmmntQnaDTO> getBoardQnaCommnetList(long boardId);

}
