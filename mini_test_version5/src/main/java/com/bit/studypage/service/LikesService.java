package com.bit.studypage.service;

import com.bit.studypage.entity.Board;
import com.bit.studypage.entity.Likes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LikesService {
    List<Likes> getLike(Long userId);
    Page<Likes> likesPage(Long userId, Pageable pageable);
    void delLike(Long likeId);
}
