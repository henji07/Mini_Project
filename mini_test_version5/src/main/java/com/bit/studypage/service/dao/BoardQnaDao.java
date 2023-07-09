package com.bit.studypage.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.studypage.dto.BoardCmmntQnaDTO;
import com.bit.studypage.dto.BoardQnaDTO;

@Mapper
public interface BoardQnaDao {
	
	List<BoardCmmntQnaDTO> selectCommentList(long boardId);
	
	BoardQnaDTO selectBoardQnaInfo(long boardId);
	
}
