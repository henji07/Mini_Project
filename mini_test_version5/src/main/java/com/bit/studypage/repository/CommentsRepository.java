package com.bit.studypage.repository;


import com.bit.studypage.entity.Comments;
import com.bit.studypage.entity.board.CommentQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentsRepository extends JpaRepository<CommentQna,Long> {
    List<CommentQna> findByUserId(Long userId);
    String countByUserId(Long userId);
    Page<CommentQna> findByUserIdOrderById(Pageable pageable,Long usersId);
    void deleteAllByBoardId(Long boardId);

}
