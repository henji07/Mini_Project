package com.bit.studypage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.studypage.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    //댓글 목록
    List<Comment> findAllByBoardBoardId(long boardId);

    //댓글 수
    int countByBoard_BoardId(Long boardId);

}