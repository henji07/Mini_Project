package com.bit.studypage.service;

import com.bit.studypage.entity.Board;
import com.bit.studypage.entity.Likes;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList(String userNickname);
    String getCountBoard(String userNickname);
    Board getBoard(Long boardNo);
    List<Board> getBoardList(List<Likes> likesList);
}
