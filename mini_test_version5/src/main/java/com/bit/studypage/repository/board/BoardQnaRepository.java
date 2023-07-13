package com.bit.studypage.repository.board;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bit.studypage.dto.board.BoardQnaDTO;
import com.bit.studypage.entity.board.BoardQna;

//JpaRepository를 상속받도록 함으로써 기본적인 동작이 모두 가능
//JpaRepository<대상으로 지정할 엔티티, 해당 엔티티의 PK의 타입>.
@Repository
public interface BoardQnaRepository extends JpaRepository<BoardQna, Long> {

	//조회수 증가
	@Transactional
	@Modifying
	@Query("update BoardQna b set b.boardCnt = b.boardCnt + 1 where b.boardId = :boardId")
	void increaseViewCount(@Param("boardId") long boardId);
	
	//페이징 
	Page<BoardQna> findAllByOrderByBoardIdDesc(Pageable pageable);
	
	//게시판 제목을 기반으로 게시판을 검색
	@Query("SELECT b FROM BoardQna b WHERE b.boardTitle LIKE %:searchKeyword%")
	Page<BoardQna> findByTitleContaining(@Param("searchKeyword") String searchKeyword, Pageable pageable);

	//게시판 제목을 기반으로 검색된 게시물의 수를 반환
	@Query("SELECT COUNT(DISTINCT b.boardId) FROM BoardQna b WHERE b.boardTitle LIKE %:searchKeyword%")
	long countByTitleContaining(@Param("searchKeyword") String searchKeyword);

	//카테고리별로 찾아가기 
	Page<BoardQna> findByBoardMaincate(String category, Pageable pageable);

	//카테고리 별 페이지 수 
	long countByBoardMaincate(String category);

	//boardMaincate에 해당하는 모든 BoardQnaDTO를 반환 - 서브 카테고리 때문에 만든 거 
	//List<BoardQna> findAllByBoardMaincate(String boardMaincate);

	
}

