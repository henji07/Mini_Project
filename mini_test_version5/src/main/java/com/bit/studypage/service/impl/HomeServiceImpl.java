package com.bit.studypage.service.impl;

import com.bit.studypage.entity.SearchBoard;
import com.bit.studypage.entity.board.BoardQna;
import com.bit.studypage.repository.HomeRepository;
import com.bit.studypage.repository.board.BoardQnaRepository;
import com.bit.studypage.service.BoardQnaService;
import com.bit.studypage.service.HomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private final HomeRepository homeRepository;

    private final BoardQnaRepository boardQnaRepository;

    @Autowired
    public HomeServiceImpl(HomeRepository homeRepository, BoardQnaRepository boardQnaRepository) {
        this.homeRepository = homeRepository;
        this.boardQnaRepository = boardQnaRepository;
    }

    @Override
    public List<SearchBoard> getTop12Posts() {
        // TODO Auto-generated method stub
        return homeRepository.findTop12ByOrderBySearchCntDesc();
    }

    @Override
    public List<BoardQna> getTop12PostsByCategory(String category) {
        return boardQnaRepository.findBoardMaincateTop12ByOrderByBoardCntDesc(category);
    }

    @Override
    public List<SearchBoard> getTop12PostsByCommentCount() {
        return homeRepository.findTop12ByOrderByCommentCountDesc();
    }


}
