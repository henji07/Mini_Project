package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Board;
import com.bit.studypage.repository.BoardRepository;
import com.bit.studypage.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    BoardRepository boardRepository;

    @Autowired
    BoardServiceImpl(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    };
    @Override
    public List<Board> getBoardList(String userNickname){
        return boardRepository.findByBoardWriter(userNickname);
    }

    @Override
    public String getCountBoard(String userNickname) {
        return boardRepository.countByBoardWriter(userNickname);
    }

    @Override
    public Board getBoard(Long boardNo) {
        return boardRepository.findById(boardNo).get();
    }
}
