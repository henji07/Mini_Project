package com.bit.studypage.service;

import java.util.List;

import com.bit.studypage.dto.BoardQnaDTO;
import com.bit.studypage.entity.BoardQna;

public interface BoardQnaService {

	//글 등록
	public void insertBoard(BoardQnaDTO boardDTO);
	
	//글 수정
	public BoardQnaDTO updateBoard(BoardQnaDTO boardDTO);
	
	//글 삭제
	public void deleteBoard(long boardId);
	
	//글 상세 조회
	public BoardQnaDTO getBoardDetail(long boardId);
	
	//글 목록 조회 
	public List<BoardQnaDTO> getBoardList(int pageNum);

	//전체 페이지 수 반환 
	public Object getTotalPages();
}
