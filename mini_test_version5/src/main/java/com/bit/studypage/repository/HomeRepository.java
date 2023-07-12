package com.bit.studypage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bit.studypage.entity.SearchBoard;

public interface HomeRepository extends JpaRepository<SearchBoard, Long> {

    @Query(value = "SELECT s FROM SearchBoard s ORDER BY s.searchCnt DESC")
    List<SearchBoard> findTop12ByOrderBySearchCntDesc();

    @Query(value = "SELECT s FROM SearchBoard s WHERE s.searchCate = :category ORDER BY s.searchCnt DESC")
    List<SearchBoard> findCateTop12ByOrderBySearchCntDesc(@Param("category")String category);

    @Query(value = "SELECT s FROM SearchBoard s ORDER BY s.commentCount DESC")
    List<SearchBoard> findTop12ByOrderByCommentCountDesc();



}