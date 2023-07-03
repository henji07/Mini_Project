package com.bit.studypage.repository;

import com.bit.studypage.entity.Board;
import com.bit.studypage.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentsRepository extends JpaRepository<Comments,Long> {
    List<Comments> findByCommWriter(String userNickname);
    String countByCommWriter(String commWriter);
    Page<Comments> findByCommWriterOrderByCommentId(Pageable pageable,String commWriter);
    void deleteAllByPostId(Long postId);

}
