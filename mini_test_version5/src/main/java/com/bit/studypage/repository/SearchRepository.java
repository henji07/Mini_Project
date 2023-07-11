package com.bit.studypage.repository;

import com.bit.studypage.entity.SearchBoard;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<SearchBoard, Long> {
    // 키워드로 검색하여 게시물을 가져오는 메서드
    Page<SearchBoard> findBySearchContentContaining(String keyword, Pageable pageable);

    // 검색어가 제목에 포함된 게시물 검색을 위한 커스텀 쿼리 메소드(대소문자 구분 없이)
    List<SearchBoard> findBySearchTitleContainingIgnoreCase(String searchKeyword);
    // 검색어가 내용에 포함된 게시물 검색을 위한 커스텀 쿼리 메소드 (대소문자 구분 없이)
    List<SearchBoard> findBySearchContentContainingIgnoreCase(String searchKeyword);

    // 커스텀 쿼리 메서드 예시: 제목과 내용에 대한 OR 연산을 수행하는 커스텀 쿼리 메서드
    @Query("SELECT s FROM SearchBoard s WHERE LOWER(s.searchTitle) LIKE %:keyword% OR LOWER(s.searchContent) LIKE %:keyword%  ")
    List<SearchBoard> findBysearchTitleOrsearchContent(@Param("keyword") String keyword);

    List<SearchBoard> findBysearchCate(String Category);
    // 선택한 카테고리에 해당하는 게시물을 검색하기 위한 커스텀 쿼리 메서드

//    List<SearchBoard> findBysearchTitleOrsearchContent(String keyword);

    List<SearchBoard> findBysearchTitleContaining(String keyword);

    List<SearchBoard> findBysearchContentContaining(String keyword);
    
    
}