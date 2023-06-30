package com.bit.studypage.service.impl;

import com.bit.studypage.DTO.CommDTO;
import com.bit.studypage.entity.Comments;
import com.bit.studypage.repository.BoardRepository;
import com.bit.studypage.repository.CommentsRepository;
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
    @Autowired
    CommentsServiceImpl(CommentsRepository commentsRepository,BoardRepository boardRepository){
        this.commentsRepository = commentsRepository;
        this.boardRepository = boardRepository;
    }
    @Override
    public List<Comments> getCommentsList(String userNickname){
        return commentsRepository.findByCommWriter(userNickname);
    }
    @Override
    public Page<Comments> getCommentsPage(Pageable pageable,String userNickname){
        return commentsRepository.findByCommWriterOrderByCommentId(pageable,userNickname);
    }

    @Override
    public List<CommDTO> getCommList(String userNickname){
        List<CommDTO> commList = new ArrayList<>();
        for (Comments c : commentsRepository.findByCommWriter(userNickname)) {
            CommDTO cdto = c.entityToDto();
            cdto.setPostTitle(boardRepository.findByBoardId(c.getPostId()).getBoardTitle());
            commList.add(cdto);
        }
        return commList;
    }

    @Override
    public void delComm(Long commId) {
        commentsRepository.deleteById(commId);
    }

    @Override
    public String getCountComm(String userNickname) {
        return commentsRepository.countByCommWriter(userNickname);
    }
}
