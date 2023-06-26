package com.bit.studypage.service;

import java.util.List;

import com.bit.studypage.entity.Board;

public interface BoardService {

	//글 등록
	void insertBoard(Board board);
	
	//글 수정
	void updateBoard(Board board);
	
	//글 삭제
	void deleteBoard(long boardId);
	
	//글 상세 조회
	Board getBoard(long boardId);
	
	//글 목록 조회 
	List<Board> getBoardList();
}
