package com.bit.studypage.repository;

import com.bit.studypage.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Long> {
    List<Comments> findByCommWriter(String userNickname);
}
