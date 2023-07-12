package com.bit.studypage.service.impl;

import com.bit.studypage.entity.SearchBoard;
import com.bit.studypage.repository.SearchRepository;
import com.bit.studypage.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private SearchRepository searchRepository;

    @Autowired
    public SearchServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public Page<SearchBoard> searchBoardPagelist(Pageable pageable, String keyword) {
        if(keyword.equals("") || keyword == null) {
            // 모든 검색 게시물 목록을 가져오는 메서드 호출
            return searchRepository.findAll(pageable);
        } else {
            return searchRepository.findBySearchTitleContainingOrSearchContentContaining(keyword, keyword, pageable);
        }
    }


    @Override
    public List<SearchBoard> searchBoardByTitleContent(String keyword) {
        // 제목 또는 내용에 특정 검색어가 포함된 게시물을 검색하는 메서드
        return searchRepository.findBysearchTitleOrsearchContent(keyword);
    }

    @Override
    public List<SearchBoard> searchBoardByTitle(String keyword) {
        // 제목에 특정 검색어가 포함된 게시물을 검색하는 메서드
        return searchRepository.findBysearchTitleContaining(keyword);
    }

    @Override
    public List<SearchBoard> searchBoardByContent(String keyword) {
        // 내용에 특정 검색어가 포함된 게시물을 검색하는 메서드
        return searchRepository.findBysearchContentContaining(keyword);
    }

    @Override
    public List<SearchBoard> searchBoardByCategory(String category) {
        // 카테고리에 해당하는 게시물을 검색하는 메서드
        return searchRepository.findBysearchCate(category);
    }

    @Override
    public List<SearchBoard> searchBoardlist() {
        return searchRepository.findAll();
    }
    @Override
    public SearchBoard searchDetailBoard(Long searchBoardId){
        return searchRepository.findById(searchBoardId).get();
    }











}