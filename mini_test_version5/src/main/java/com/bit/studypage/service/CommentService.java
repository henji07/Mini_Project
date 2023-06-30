package com.bit.studypage.service;

import java.util.List;

import com.bit.studypage.dto.CommentDTO;
import com.bit.studypage.entity.Users;

public interface CommentService {
	
	CommentDTO writeComment(long boardId, CommentDTO commentDto, Users user);
    List<CommentDTO> getComments(long boardId);
    String deleteComment(int commentId);

}
