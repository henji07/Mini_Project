package com.bit.studypage.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
	public List<BoardDTO> getBoardList(int pageNum) {
		
		int pageSize = 5;  // 한 페이지에 보여질 게시글 수
		
		//Spring Data JPA의 Pageable 인터페이스와 Page 클래스를 사용해서 페이지네이션을 구현
		//PageRequest의 of 메소드를 사용해 페이지 번호와 페이지 크기를 입력하고, 정렬 조건을 지정
		//페이지 번호는 0부터 시작하므로 사용자가 입력하는 페이지 번호에서 1을 빼줌
		//boardRepository의 findAll 메소드에 pageable 객체를 전달해 원하는 페이지의 데이터를 가져온다.
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "boardId"));
		Page<Board> pageInfo =  boardRepository.findAll(pageable);
		
		//이를 BoardDTO 객체 리스트로 변환한 후 반환
		List<Board> boardList = pageInfo.getContent();
		
		List<BoardDTO> dataList = new ArrayList<>();
		
		if(ObjectUtils.isNotEmpty(boardList)) {
			for(Board b : boardList) {
				
				BoardDTO dto = new BoardDTO();
				BeanUtils.copyProperties(b, dto);
				dataList.add(dto);
			}
		}
		
		return dataList;
	}

	//전체 페이시 수 반환 
	@Override
	public Object getTotalPages() {
		int pageSize = 5;
	    long totalBoards = boardRepository.count();
	    return (int) Math.ceil((double) totalBoards / pageSize);
	}


}
