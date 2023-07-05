package com.bit.studypage.service;

import java.util.List;

import com.bit.studypage.dto.CommentDTO;
import com.bit.studypage.entity.Users;

public interface CommentService {
	// 댓글 작성 
	CommentDTO writeComment(long boardId, CommentDTO commentDto, Users user);
	
	//댓글 목록
    List<CommentDTO> getComments(long boardId);
    
    //댓글 삭제 
    String deleteComment(int commentId);

}
