package com.bit.studypage.service;

import com.bit.studypage.entity.Board;
import com.bit.studypage.entity.Likes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    List<Board> getBoardList(String userNicknamez);
    String getCountBoard(String userNickname);
    Board getBoard(Long boardNo);
    List<Board> getBoardList(List<Likes> likesList);
    Page<Board> pageList(String writer, Pageable pageable);
    void delBoard(Long boardId);
}
