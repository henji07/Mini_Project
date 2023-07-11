package com.bit.studypage.service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.studypage.dto.BoardCmmntQnaDTO;
import com.bit.studypage.dto.BoardQnaDTO;

//@Mapper
public interface BoardQnaDao {
	//댓글 리스트 
	List<BoardCmmntQnaDTO> selectCommentList(long boardId);
	
	//글 상세 조회 
	BoardQnaDTO selectBoardQnaInfo(long boardId);

	//댓글 수정 
	int updateComment(Map<String, Object> params);
	
}
