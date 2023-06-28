package com.bit.studypage.service;

import com.bit.studypage.entity.Board;

import java.util.List;

public interface BoardService {
    public List<Board> getBoardList(String userNickname);
    String getCountBoard(String userNickname);
    Board getBoard(Long boardNo);
}
