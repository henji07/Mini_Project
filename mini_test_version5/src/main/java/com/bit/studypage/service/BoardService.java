package com.bit.studypage.service;

import com.bit.studypage.entity.board.BoardQna;
import com.bit.studypage.entity.Likes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    List<BoardQna> getBoardQnaList(String userNicknamez);
    String getCountBoardQna(String userNickname);
    BoardQna getBoardQna(Long boardNo);
    List<BoardQna> getBoardQnaList(List<Likes> likesList);
    Page<BoardQna> pageList(String writer, Pageable pageable);
    void delBoardQna(Long boardId);

    // SearchBoardQna related methods - 유진추가
    Page<BoardQna> boardQnaPagelist(Pageable pageable);
    List<BoardQna> boardQnaByTitleContent(String keyword);
    Page<BoardQna> boardQnaByTitleContent(String keyword,Pageable pageable);
    List<BoardQna> boardQnaByTitle(String keyword);
    Page<BoardQna> boardQnaByTitle(String keyword,Pageable pageable);
    List<BoardQna> boardQnaByContent(String keyword);
    Page<BoardQna> boardQnaByContent(String keyword, Pageable pageable);
    List<BoardQna> boardQnaByCategory(String category);
    List<BoardQna> boardQnaList();
    BoardQna detailBoardQna(Long boardId);
    Page<BoardQna> boardQnaByCategory(String category,Pageable pageable);
    Page<BoardQna> boardQnaList(Pageable pageable);
}
