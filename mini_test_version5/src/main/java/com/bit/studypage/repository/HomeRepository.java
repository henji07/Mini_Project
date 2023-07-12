package com.bit.studypage.repository;

import com.bit.studypage.entity.SearchBoard;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeRepository extends JpaRepository<SearchBoard, Long> {

    @Query(value = "SELECT s FROM SearchBoard s ORDER BY s.searchCnt DESC")
    List<SearchBoard> findTop12ByOrderBySearchCntDesc();

    @Query(value = "SELECT s FROM SearchBoard s WHERE s.searchCate = :category ORDER BY s.searchCnt DESC")
    List<SearchBoard> findCateTop12ByOrderBySearchCntDesc(@Param("category")String category);

    @Query(value = "SELECT s FROM SearchBoard s ORDER BY s.commentCount DESC")
    List<SearchBoard> findTop12ByOrderByCommentCountDesc();



}
