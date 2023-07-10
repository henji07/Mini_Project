package com.bit.studypage.repository;

import com.bit.studypage.entity.Likes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {
    List<Likes> findByUsersId(Long userId);
    Page<Likes> findByUsersIdOrderByUsersId(Long userId, Pageable pageable);
    void deleteAllByPostId(Long postId);
}
