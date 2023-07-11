package com.bit.studypage.service.dao;

import java.util.List;

import com.bit.studypage.dto.BoardCmmntQnaDTO;
import com.bit.studypage.dto.BoardQnaDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardQnaDao {
	//댓글 리스트
	List<BoardCmmntQnaDTO> selectCommentList(long boardId);

	//글 상세 조회
	BoardQnaDTO selectBoardQnaInfo(long boardId);

}
