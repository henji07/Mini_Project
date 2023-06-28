package com.bit.studypage.service;

import com.bit.studypage.entity.Comments;

import java.util.List;

public interface CommentsService {
    List<Comments> getCommentsList(String userNickname);
    String getCountComm(String userNickname);
}
