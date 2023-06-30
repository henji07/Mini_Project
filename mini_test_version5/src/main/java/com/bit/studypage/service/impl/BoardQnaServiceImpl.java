package com.bit.studypage.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bit.studypage.dto.BoardQnaDTO;
import com.bit.studypage.dto.FileDTO;
import com.bit.studypage.entity.BoardQna;
import com.bit.studypage.entity.FileEntity;
import com.bit.studypage.repository.BoardQnaRepository;
import com.bit.studypage.repository.CommentRepository;
import com.bit.studypage.repository.FileRepository;
import com.bit.studypage.service.BoardQnaService;
import com.bit.studypage.service.FileStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor //생성자 주입 
public class BoardQnaServiceImpl implements BoardQnaService {
	
	private final BoardQnaRepository boardRepository;
	private final FileRepository fileRepository;
	private final FileStorageService fileStorageService;
	private final CommentRepository commentRepository;
	
	@Value("${app.file-upload-dir}")
    private String fileUploadDir;
	
	//글 등록
	@Override
	public BoardQna insertBoard(BoardQnaDTO boardDTO, MultipartFile file) {
		
		// DTO를 엔티티로 변환
		BoardQna board = BoardQna.builder().data(boardDTO).build();
		
		// 파일 처리
		if (file != null && !file.isEmpty()) {
            try {
                
                // 파일 시스템에 저장
                String filePath = fileStorageService.storeFile(file, fileUploadDir);
                
                // 파일의 정보를 저장하고 데이터베이스에 추가
                String fileName = file.getOriginalFilename();
                String fileType = file.getContentType();
                
                FileEntity fileEntity = new FileEntity(fileName, fileType, filePath);
                fileRepository.save(fileEntity);
                
                // 파일 엔티티를 보드 엔티티에 설정
                board.setFileEntity(fileEntity);
            } catch (IOException ex) {
                throw new RuntimeException("Could not store file " + file.getOriginalFilename(), ex);
            }
        }
		// 보드 엔티티를 데이터베이스에 저장하고 결과를 반환
		BoardQna entity = boardRepository.save(board);
		
		if(entity != null) {
			long boardId = entity.getBoardId();		
			log.debug("boardId = {}", boardId);
		}
		return entity;
	}

	//글 수정
	public BoardQnaDTO updateBoard(BoardQnaDTO boardDTO) {
		
		System.out.println(boardDTO.toString());
		BoardQnaDTO dto = null;
		Optional<BoardQna> option = boardRepository.findById(boardDTO.getBoardId());
		
		if(ObjectUtils.isNotEmpty(option)) {
			BoardQna board = option.get();
			
			if(ObjectUtils.isNotEmpty(board)) {
				board.updateContent(boardDTO);
				BoardQna entity = boardRepository.save(board);
				
				if(ObjectUtils.isNotEmpty(entity)) {
					dto = new BoardQnaDTO();
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
	public BoardQnaDTO getBoardDetail(long boardId) {
		
		BoardQnaDTO dto = new BoardQnaDTO();
		
		Optional<BoardQna> option = boardRepository.findById(boardId);
		
		if(ObjectUtils.isNotEmpty(option)) {
			BoardQna board = option.get();
			
			if(ObjectUtils.isNotEmpty(board)) {
				board.updateReadCount(board.getBoardCnt() + 1); // 조회수 증가
				boardRepository.save(board); // 변경된 조회수 저장
				
				BeanUtils.copyProperties(board, dto);
				
				// 파일 정보가 있을 경우, FileDTO를 생성하고 BoardQnaDTO에 설정
	            if(board.getFileEntity() != null) {
	                FileDTO fileDTO = new FileDTO();
	                BeanUtils.copyProperties(board.getFileEntity(), fileDTO);
	                dto.setFile(fileDTO);
	            }
			}
	    }
	    return dto;
	}

	//글 목록 
	@Override
	public List<BoardQnaDTO> getBoardList(int pageNum) {
		
		int pageSize = 5;  // 한 페이지에 보여질 게시글 수
		
		//Spring Data JPA의 Pageable 인터페이스와 Page 클래스를 사용해서 페이지네이션을 구현
		//PageRequest의 of 메소드를 사용해 페이지 번호와 페이지 크기를 입력하고, 정렬 조건을 지정
		//페이지 번호는 0부터 시작하므로 사용자가 입력하는 페이지 번호에서 1을 빼줌
		//boardRepository의 findAll 메소드에 pageable 객체를 전달해 원하는 페이지의 데이터를 가져온다.
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "boardId"));
		Page<BoardQna> pageInfo =  boardRepository.findAll(pageable);
		
		//이를 BoardDTO 객체 리스트로 변환한 후 반환
		List<BoardQna> boardList = pageInfo.getContent();
		
		List<BoardQnaDTO> dataList = new ArrayList<>();
		
		if(ObjectUtils.isNotEmpty(boardList)) {
			for(BoardQna b : boardList) {
				
				BoardQnaDTO dto = new BoardQnaDTO();
				BeanUtils.copyProperties(b, dto);
				
				// 댓글 수 조회
	            int commentCount = getCommentCount(b.getBoardId());
	            dto.setCommentCount(commentCount);
				
				dataList.add(dto);
			}
		}
		
		return dataList;
	}

	//전체 페이지 수 반환 
	@Override
	public Object getTotalPages() {
		int pageSize = 5;
	    long totalBoards = boardRepository.count();
	    return (int) Math.ceil((double) totalBoards / pageSize);
	}

	//파일 목록
	@Override
	public List<FileEntity> getBoardFileList(long boardId) {
		
		return fileRepository.findByBoardQnaBoardId(boardId);
	}
	
	//댓글 수 
	public int getCommentCount(long boardId) {
	    return commentRepository.countByBoard_BoardId(boardId);
	}


}
