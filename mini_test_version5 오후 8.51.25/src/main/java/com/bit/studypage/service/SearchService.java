package com.bit.studypage.service;

import com.bit.studypage.entity.SearchBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {
    // 모든 검색 게시물 목록을 반환하는 메서드
    Page<SearchBoard> searchBoardPagelist(Pageable pageable, String keyword);



    // 제목 또는 내용에 특정 검색어가 포함된 게시물을 검색하는 메서드
    List<SearchBoard> searchBoardByTitleContent(String keyword);

    // 제목에 특정 검색어가 포함된 게시물을 검색하는 메서드
    List<SearchBoard> searchBoardByTitle(String keyword);

    // 내용에 특정 검색어가 포함된 게시물을 검색하는 메서드
    List<SearchBoard> searchBoardByContent(String keyword);

    // 카테고리에 해당하는 게시물을 검색하는 메서드
    List<SearchBoard> searchBoardByCategory(String category);


    List<SearchBoard> searchBoardlist();

    SearchBoard searchDetailBoard(Long searchBoardId);


}