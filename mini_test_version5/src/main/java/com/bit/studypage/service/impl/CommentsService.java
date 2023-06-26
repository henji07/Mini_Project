package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Comments;
import com.bit.studypage.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    CommentsRepository commentsRepository;
    @Autowired
    CommentsService(CommentsRepository commentsRepository){
        this.commentsRepository = commentsRepository;
    }
    public List<Comments> getCommentsList(String userNickname){
        return commentsRepository.findByCommWriter(userNickname);
    }
}
