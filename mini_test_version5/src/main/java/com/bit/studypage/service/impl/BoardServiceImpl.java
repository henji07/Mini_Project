package com.bit.studypage.service.impl;

import com.bit.studypage.entity.board.BoardQna;
import com.bit.studypage.entity.board.BoardQna;
import com.bit.studypage.entity.Likes;
import com.bit.studypage.repository.BoardRepository;
import com.bit.studypage.repository.CommentsRepository;
import com.bit.studypage.repository.LikesRepository;
import com.bit.studypage.repository.board.BoardQnaRepository;
import com.bit.studypage.service.BoardQnaService;
import com.bit.studypage.service.BoardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    BoardRepository boardRepository;
    LikesRepository likesRepository;
    CommentsRepository commentsRepository;

    @Autowired
    BoardServiceImpl(BoardRepository boardRepository, LikesRepository likesRepository, CommentsRepository commentsRepository){
        this.boardRepository = boardRepository;
        this.likesRepository = likesRepository;
        this.commentsRepository = commentsRepository;
    }

    @Override
    public List<BoardQna> getBoardQnaList(String userNickname){
        return boardRepository.findByBoardWriter(userNickname);
    }

    @Override
    public List<BoardQna> getBoardQnaList(List<Likes> likesList) {
        List<BoardQna> boardQnaList = new ArrayList<>();
        for(Likes l : likesList){
            BoardQna boardQna = boardRepository.findByBoardId(l.getPostId());
            boardQnaList.add(boardQna);
        }
        return boardQnaList;
    }

    @Override
    public Page<BoardQna> pageList(String writer, Pageable pageable) {
        return boardRepository.findByBoardWriterOrderByBoardIdDesc(writer, pageable);
    }

    @Override
    @Transactional
    public void delBoardQna(Long boardId) {
        boardRepository.deleteById(boardId);
        commentsRepository.deleteAllByPostId(boardId);
        likesRepository.deleteAllByPostId(boardId);
    }

    @Override
    public String getCountBoardQna(String userNickname) {
        return boardRepository.countByBoardWriter(userNickname);
    }

    @Override
    public BoardQna getBoardQna(Long boardNo) {
        return boardRepository.findById(boardNo).get();
    }

    // SearchBoardQna related methods - 유진추가
    public Page<BoardQna> boardQnaPagelist(Pageable pageable) {
        // 모든 검색 게시물 목록을 가져오는 메서드 호출
        return boardRepository.findAll(pageable);
    }

    public List<BoardQna> boardQnaByTitleContent(String keyword) {
        // 제목 또는 내용에 특정 검색어가 포함된 게시물을 검색하는 메서드
        return boardRepository.findByBoardTitleOrSearchContent(keyword);
    }

    public List<BoardQna> boardQnaByTitle(String keyword) {
        // 제목에 특정 검색어가 포함된 게시물을 검색하는 메서드
        return boardRepository.findByBoardTitleContaining(keyword);
    }
    public Page<BoardQna> boardQnaByTitle(String keyword, Pageable pageable) {
        // 제목에 특정 검색어가 포함된 게시물을 검색하는 메서드
        return boardRepository.findByBoardTitleContaining(keyword, pageable);
    }

    public List<BoardQna> boardQnaByContent(String keyword) {
        // 내용에 특정 검색어가 포함된 게시물을 검색하는 메서드
        return boardRepository.findByBoardContentContaining(keyword);
    }
    public Page<BoardQna> boardQnaByContent(String keyword, Pageable pageable) {
        // 내용에 특정 검색어가 포함된 게시물을 검색하는 메서드
        return boardRepository.findByBoardContentContaining(keyword, pageable);
    }

    public List<BoardQna> boardQnaByCategory(String category) {
        // 카테고리에 해당하는 게시물을 검색하는 메서드
        return boardRepository.findByBoardMaincate(category);
    }

    public List<BoardQna> boardQnaList() {
        return boardRepository.findAll();
    }
    public Page<BoardQna> boardQnaByCategory(String category, Pageable pageable) {
        // 카테고리에 해당하는 게시물을 검색하는 메서드
        return boardRepository.findByBoardMaincate(category, pageable);
    }

    public Page<BoardQna> boardQnaList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
    public Page<BoardQna> boardQnaByTitleContent(String keyword, Pageable pageable){
        return boardRepository.findByBoardTitleOrSearchContent(keyword,pageable);
    }
    public BoardQna detailBoardQna(Long boardId){
        return boardRepository.findById(boardId).get();
    }
}
