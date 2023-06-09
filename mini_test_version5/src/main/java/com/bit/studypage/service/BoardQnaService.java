package com.bit.studypage.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.bit.studypage.dto.board.BoardQnaDTO;
import com.bit.studypage.dto.board.FileQnaDTO;
import com.bit.studypage.entity.board.BoardQna;
import com.bit.studypage.entity.board.FileQna;

public interface BoardQnaService {

	//글 등록
	public Map<String, Object> insertBoard(BoardQnaDTO boardDTO, List<MultipartFile> files,String userId);
	
	//글 수정
	public Map<String, Object> updateBoard(BoardQnaDTO boardDTO, List<MultipartFile> files);
	
	//글 삭제
	public void deleteBoard(long boardId);
	
	//글 상세 조회
	public BoardQnaDTO getBoardDetail(long boardId, long userId);
	
	//글 목록 조회 
	public List<BoardQnaDTO> getBoardList(int pageNum, String sortOption, String category, String subcategory);

	//전체 페이지 수 반환 
	//public Object getTotalPages();
	
	//파일 정보 가져오기
	public FileQnaDTO inqurityFileInfo(long id);
	
	//게시판 검색 기능을 구현
	public List<BoardQnaDTO> searchBoardsByTitle(String searchKeyword, int pageNum, String sortOption);
	
	//검색된 게시물을 기반으로 총 페이지 수를 계산
	public int getSearchTotalPages(String keyword);

	//대분류 카테고리 
	public List<BoardQnaDTO> getBoardListByCategory(String category, int pageNum, String sortOption);

	//대분류 카테고리 총 페이지 수 
	public Object getTotalPagesByCategory(String category);

	/* 카테고리 별 게시물 전체 페이지 수 반환 */
	public Object getTotalPages(String category, String subcategory);

	//서브카테고리 
	//public String getSubcategories(String boardMaincate);

}
