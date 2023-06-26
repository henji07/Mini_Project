package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Board;
import com.bit.studypage.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    BoardRepository boardRepository;

    @Autowired
    BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    };
    public List<Board> getBoardList(String userNickname){
        return boardRepository.findByBoardWriter(userNickname);
    }
}
