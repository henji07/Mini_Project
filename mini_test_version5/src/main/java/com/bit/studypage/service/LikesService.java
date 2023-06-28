package com.bit.studypage.service;

import com.bit.studypage.entity.Board;
import com.bit.studypage.entity.Likes;

import java.util.List;

public interface LikesService {
    List<Likes> getLike(Long userId);
}
