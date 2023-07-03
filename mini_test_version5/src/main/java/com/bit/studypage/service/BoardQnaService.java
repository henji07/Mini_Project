package com.bit.studypage.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bit.studypage.dto.BoardQnaDTO;
import com.bit.studypage.dto.FileQnaDTO;
import com.bit.studypage.entity.BoardQna;
import com.bit.studypage.entity.FileQnaEntity;

public interface BoardQnaService {

	//글 등록
	public Map<String, Object> insertBoard(BoardQnaDTO boardDTO, List<MultipartFile> files);
	
	//글 수정
	public Map<String, Object> updateBoard(BoardQnaDTO boardDTO);
	
	//글 삭제
	public void deleteBoard(long boardId);
	
	//글 상세 조회
	public BoardQnaDTO getBoardDetail(long boardId);
	
	//글 목록 조회 
	public List<BoardQnaDTO> getBoardList(int pageNum);

	//전체 페이지 수 반환 
	public Object getTotalPages();
	
	//파일 정보 가져오기
	public FileQnaDTO inqurityFileInfo(long id);


}
