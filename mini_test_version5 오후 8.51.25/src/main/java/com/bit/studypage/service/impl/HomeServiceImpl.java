package com.bit.studypage.service.impl;

import com.bit.studypage.entity.SearchBoard;
import com.bit.studypage.repository.HomeRepository;
import com.bit.studypage.service.HomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private final HomeRepository homeRepository;

    @Autowired
    public HomeServiceImpl(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    @Override
    public List<SearchBoard> getTop12Posts() {
        // TODO Auto-generated method stub
        return homeRepository.findTop12ByOrderBySearchCntDesc();
    }

    @Override
    public List<SearchBoard> getTop12PostsByCategory(String category) {
        return homeRepository.findCateTop12ByOrderBySearchCntDesc(category);
    }

    @Override
    public List<SearchBoard> getTop12PostsByCommentCount() {
        return homeRepository.findTop12ByOrderByCommentCountDesc();
    }


}
