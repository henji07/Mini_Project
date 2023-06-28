package com.bit.studypage.service;

import com.bit.studypage.domain.entity.Board;
import com.bit.studypage.domain.repository.BoardRepository;
import com.bit.studypage.dto.BoardDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boardList) {
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .boardTitle(board.getBoardTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    public List<Board> findBoardList() {
        return boardRepository.findAll();
    }
}