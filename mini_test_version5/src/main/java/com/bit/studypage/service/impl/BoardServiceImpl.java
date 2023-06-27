package com.bit.studypage.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bit.studypage.dto.BoardDTO;
import com.bit.studypage.entity.Board;
import com.bit.studypage.repository.BoardRepository;
import com.bit.studypage.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor //생성자 주입 
public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository boardRepository;
	
	//글 등록
	@Override
	public void insertBoard(BoardDTO boardDTO) {
		
		Board board = Board.builder().data(boardDTO).build();
		
		Board entity = boardRepository.save(board);
		
		if(ObjectUtils.isNotEmpty(entity)) {
			long boardId = entity.getBoardId();
			
			log.debug("boardId = {}", boardId);
		}
	}

	//글 수정
	public BoardDTO updateBoard(BoardDTO boardDTO) {
		
		System.out.println(boardDTO.toString());
		BoardDTO dto = null;
		Optional<Board> option = boardRepository.findById(boardDTO.getBoardId());
		
		if(ObjectUtils.isNotEmpty(option)) {
			Board board = option.get();
			
			if(ObjectUtils.isNotEmpty(board)) {
				board.updateContent(boardDTO);
				Board entity = boardRepository.save(board);
				
				if(ObjectUtils.isNotEmpty(entity)) {
					dto = new BoardDTO();
					BeanUtils.copyProperties(entity, dto);
				}
			}
			
		}
		
		return dto;
	}
	
	//글 삭제
	public void deleteBoard(long boardId) {
		boardRepository.deleteById(boardId);
	}
	
	//글 상세 조회
	@Override
	public BoardDTO getBoardDetail(long boardId) {
		
		BoardDTO dto = new BoardDTO();
		
		Optional<Board> option = boardRepository.findById(boardId);
		
		if(ObjectUtils.isNotEmpty(option)) {
			Board board = option.get();
			
			if(ObjectUtils.isNotEmpty(board)) {
				board.updateReadCount(board.getBoardCnt() + 1); // 조회수 증가
				boardRepository.save(board); // 변경된 조회수 저장
				
				BeanUtils.copyProperties(board, dto);
			}
	    }
	    return dto;
	}

	//글 목록 
	@Override
	public List<BoardDTO> getBoardList() {
		
		List<BoardDTO> dataList = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(0, 5);
		Page<Board> pageInfo =  boardRepository.findAllByOrderByBoardIdDesc(pageable);
		
		List<Board> boardList = pageInfo.getContent();
		if(ObjectUtils.isNotEmpty(boardList)) {
			for(Board b : boardList) {
				
				BoardDTO dto = new BoardDTO();
				BeanUtils.copyProperties(b, dto);
				dataList.add(dto);
			}
		}
		
		return dataList;
	}

}
