package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Board;
import com.bit.studypage.entity.Likes;
import com.bit.studypage.repository.BoardRepository;
import com.bit.studypage.repository.LikesRepository;
import com.bit.studypage.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {
    LikesRepository likesRepository;

    @Autowired
    private LikesServiceImpl(LikesRepository likesRepository){
        this.likesRepository = likesRepository;
    }
    @Override
    public List<Likes> getLike(Long userId) {
        return likesRepository.findByUsersId(userId);
    }

    @Override
    public Page<Likes> likesPage(Long userId, Pageable pageable) {
        return likesRepository.findByUsersIdOrderByUsersId(userId,pageable);
    }

    @Override
    public void delLike(Long likeId) {
        likesRepository.deleteById(likeId);
    }


}
