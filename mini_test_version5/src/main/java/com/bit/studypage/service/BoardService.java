package com.bit.studypage.service;

import com.bit.studypage.entity.Board;
import com.bit.studypage.entity.Likes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
<<<<<<< HEAD

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
=======
    List<Board> getBoardList(String userNicknamez);
    String getCountBoard(String userNickname);
    Board getBoard(Long boardNo);
    List<Board> getBoardList(List<Likes> likesList);
    Page<Board> pageList(String writer, Pageable pageable);
    void delBoard(Long boardId);
}
>>>>>>> 48b914e81879888cb90bdd5e68e529a1b8d1ae04
