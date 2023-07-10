package com.bit.studypage.service;

import org.springframework.security.core.Authentication;

import com.bit.studypage.dto.LikeQnaDTO;


public interface LikeQnaService {

	//좋아요
	public LikeQnaDTO insertLike(LikeQnaDTO likeDTO, Authentication authentication);

	//좋아요 취소 
	public String removeLike(long boardId, Authentication authentication);

	//좋아요 체크 여부 
	public boolean isLikedByUser(long boardId, long userId);

}
