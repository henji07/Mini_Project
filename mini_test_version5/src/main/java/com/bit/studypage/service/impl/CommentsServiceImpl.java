package com.bit.studypage.service.impl;

import com.bit.studypage.dto.CommDTO;
import com.bit.studypage.entity.Comments;
import com.bit.studypage.entity.board.CommentQna;
import com.bit.studypage.repository.BoardRepository;
import com.bit.studypage.repository.CommentsRepository;
import com.bit.studypage.repository.board.CommentQnaRepository;
import com.bit.studypage.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    CommentsRepository commentsRepository;
    BoardRepository boardRepository;
    CommentQnaRepository commentQnaRepository;
    @Autowired
    CommentsServiceImpl(CommentsRepository commentsRepository,BoardRepository boardRepository,CommentQnaRepository commentQnaRepository){
        this.commentsRepository = commentsRepository;
        this.boardRepository = boardRepository;
        this.commentQnaRepository = commentQnaRepository;
    }
    @Override
    public List<CommentQna> getCommentsList(Long userId){
        return commentsRepository.findByUserId(userId);
    }
    @Override
    public Page<CommentQna> getCommentsPage(Pageable pageable,Long usersId){
        return commentsRepository.findByUserIdOrderById(pageable,usersId);
    }

    @Override
    public List<CommentQna> getCommList(Long userId){
        return commentsRepository.findByUserId(userId);
    }

    @Override
    public void delComm(Long commId) {
        commentsRepository.deleteById(commId);
    }

    @Override
    public String getCountComm(Long userId) {
        return commentsRepository.countByUserId(userId);
    }
}
