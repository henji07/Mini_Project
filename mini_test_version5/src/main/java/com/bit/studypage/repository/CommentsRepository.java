package com.bit.studypage.repository;

import com.bit.studypage.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentsRepository extends JpaRepository<Comments,Long> {
    List<Comments> findByCommWriter(String userNickname);
    String countByCommWriter(String commWriter);
}
