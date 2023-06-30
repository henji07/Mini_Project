package com.bit.studypage.service;

import com.bit.studypage.DTO.CommDTO;
import com.bit.studypage.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface CommentsService {
    List<Comments> getCommentsList(String userNickname);
    String getCountComm(String userNickname);
    Page<Comments> getCommentsPage(Pageable pageable, String userNickname);
    List<CommDTO> getCommList(String userNickname);
    void delComm(Long commId);
}
