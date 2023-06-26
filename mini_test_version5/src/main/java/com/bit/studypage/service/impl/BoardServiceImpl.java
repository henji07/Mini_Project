package com.bit.studypage.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bit.studypage.entity.Board;
import com.bit.studypage.repository.BoardRepository;
import com.bit.studypage.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //생성자 주입 
public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository boardRepository;
	
	//글 등록
	@Override
	public void insertBoard(Board board) {
		boardRepository.save(board);
		
		//변경사항 커밋 후 저장 
		boardRepository.flush();
		
	}

	//글 수정
	public void updateBoard(Board board) {
		boardRepository.save(board);
	}
	
	//글 삭제
	public void deleteBoard(long boardId) {
		boardRepository.deleteById(boardId);
	}
	
	//글 상세 조회
	public Board getBoard(long boardId) {
		return boardRepository.findById(boardId).orElse(null);
	}

	//글 목록 
	@Override
	public List<Board> getBoardList() {
		return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
	}

}
