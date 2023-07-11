package com.bit.studypage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.studypage.entity.LikeQna;

@Repository
public interface LikeQnaRepository extends JpaRepository<LikeQna,Long> {

	//좋아요 중복 체크 
	Optional<LikeQna> findByUserIdAndBoardId(long userId, long boardId);

	//좋아요 체크 여부 
	boolean existsByUserIdAndBoardId(long userId, long boardId);

}
