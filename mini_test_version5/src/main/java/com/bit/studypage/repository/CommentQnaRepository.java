package com.bit.studypage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.studypage.dto.BoardCmmntQnaDTO;
import com.bit.studypage.entity.CommentQna;

@Repository
public interface CommentQnaRepository extends JpaRepository<CommentQna, Integer> {
	
	//댓글 목록 
   public List<CommentQna> findAllByBoardId(long boardId);
	
    //댓글 수 
   public int countByBoardId(Long boardId);

}
