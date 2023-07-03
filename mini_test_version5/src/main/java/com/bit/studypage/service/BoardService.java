package com.bit.studypage.service;

import java.util.List;

import com.bit.studypage.dto.BoardDTO;
import com.bit.studypage.entity.Board;

public interface BoardService {

	//글 등록
	public void insertBoard(BoardDTO boardDTO);
	
	//글 수정
	public BoardDTO updateBoard(BoardDTO boardDTO);
	
	//글 삭제
	public void deleteBoard(long boardId);
	
	//글 상세 조회
	public BoardDTO getBoardDetail(long boardId);
	
	//글 목록 조회 
	public List<BoardDTO> getBoardList(int pageNum);

	//전체 페이지 수 반환 
	public Object getTotalPages();
}