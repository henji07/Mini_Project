package com.bit.studypage.repository;

import com.bit.studypage.entity.board.BoardQna;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardQna,Long> {
    List<BoardQna> findByBoardWriter(String boardWriter);
    String countByBoardWriter(String boardWriter);
    BoardQna findByBoardId(Long userId);
    Page<BoardQna>findByBoardWriterOrderByBoardIdDesc(String boardWriter, Pageable pageable);

    // 키워드로 검색하여 게시물을 가져오는 메서드 - 유진추가

    // 검색어가 제목에 포함된 게시물 검색을 위한 커스텀 쿼리 메서드(대소문자 구분 없이)
    List<BoardQna> findByBoardTitleContainingIgnoreCase(String searchKeyword);

    // 검색어가 내용에 포함된 게시물 검색을 위한 커스텀 쿼리 메서드 (대소문자 구분 없이)
    List<BoardQna> findByBoardContentContainingIgnoreCase(String searchKeyword);

    // 커스텀 쿼리 메서드 예시: 제목과 내용에 대한 OR 연산을 수행하는 커스텀 쿼리 메서드
    @Query("SELECT b FROM BoardQna b WHERE LOWER(b.boardTitle) LIKE %:keyword% OR LOWER(b.boardContent) LIKE %:keyword%")
    List<BoardQna> findByBoardTitleOrSearchContent(@Param("keyword") String keyword);
    @Query("SELECT b FROM BoardQna b WHERE LOWER(b.boardTitle) LIKE %:keyword% OR LOWER(b.boardContent) LIKE %:keyword%")
    Page<BoardQna> findByBoardTitleOrSearchContent(@Param("keyword") String keyword,Pageable pageable);

    List<BoardQna> findByBoardMaincate(String Category);

    List<BoardQna> findByBoardTitleContaining(String keyword);
    Page<BoardQna> findByBoardTitleContaining(String keyword, Pageable pageable);

    List<BoardQna> findByBoardContentContaining(String keyword);
    Page<BoardQna> findByBoardContentContaining(String keyword, Pageable pageable);
    Page<BoardQna> findByBoardMaincate(String Category,Pageable pageable);
    Page<BoardQna> findAll(Pageable pageable);
}
