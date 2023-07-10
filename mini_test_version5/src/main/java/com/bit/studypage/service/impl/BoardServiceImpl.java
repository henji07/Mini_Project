package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Board;
import com.bit.studypage.entity.Likes;
import com.bit.studypage.repository.BoardRepository;
import com.bit.studypage.repository.LikesRepository;
import com.bit.studypage.service.BoardService;
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

    @Autowired
    BoardServiceImpl(BoardRepository boardRepository,LikesRepository likesRepository){
        this.boardRepository = boardRepository;
        this.likesRepository=likesRepository;
    };
    @Override
    public List<Board> getBoardList(String userNickname){
        return boardRepository.findByBoardWriter(userNickname);
    }
    @Override
    public List<Board> getBoardList(List<Likes> likesList) {
        List<Board> boardList = new ArrayList<>();
        for(Likes l : likesList){
            Board board = boardRepository.findByBoardId(l.getPostId());
            boardList.add(board);
        }
        return boardList;

    }

    @Override
    public Page<Board> pageList(String writer,Pageable pageable) {
        return boardRepository.findByBoardWriterOrderByBoardIdDesc(writer,pageable);
    }

    @Override
    public void delBoard(Long boardId) {
        boardRepository.deleteById(boardId);
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
