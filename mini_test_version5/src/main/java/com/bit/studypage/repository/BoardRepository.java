package com.bit.studypage.repository;

import com.bit.studypage.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByBoardWriter(String boardWriter);
    String countByBoardWriter(String boardWriter);
    Board findByBoardId(Long userId);
    Page<Board>findByBoardWriterOrderByBoardIdDesc(String boardWriter, Pageable pageable);
}