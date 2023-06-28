package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Comments;
import com.bit.studypage.repository.CommentsRepository;
import com.bit.studypage.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    CommentsRepository commentsRepository;
    @Autowired
    CommentsServiceImpl(CommentsRepository commentsRepository){
        this.commentsRepository = commentsRepository;
    }
    @Override
    public List<Comments> getCommentsList(String userNickname){
        return commentsRepository.findByCommWriter(userNickname);
    }

    @Override
    public String getCountComm(String userNickname) {
        return commentsRepository.countByCommWriter(userNickname);
    }
}
