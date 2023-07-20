package com.bit.studypage.service;

import com.bit.studypage.dto.CommDTO;
import com.bit.studypage.entity.Comments;
import com.bit.studypage.entity.board.CommentQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentsService {
    public List<CommentQna> getCommentsList(Long userId);
    public Page<CommentQna> getCommentsPage(Pageable pageable,Long userId);

    public List<CommentQna> getCommList(Long userId);

    public void delComm(Long commId);

    public String getCountComm(Long userId) ;
}
