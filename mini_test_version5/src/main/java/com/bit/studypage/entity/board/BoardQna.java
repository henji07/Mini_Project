package com.bit.studypage.entity.board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.bit.studypage.dto.board.BoardQnaDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Board_qna")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardQna {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="board_id")
	private Long boardId;

	@Column(name="board_title")
	private String boardTitle;

	@Column(name="board_content")
	private String boardContent;

	@Column(name="board_cnt", nullable = false)
    private int boardCnt;//조회수

	@Column(name="board_writer")
	private String boardWriter;
	@Column(name="board_reg_date")
	private String boardRegdate;

	@Column(name = "like_count")
    private long likeCount;
	
	@Column(name = "board_main_cate")
	private String boardMaincate; //대분류 카테고리 
	
	@Column(name = "subcategory")
	private String subcategory;

	@Builder
	public BoardQna(BoardQnaDTO data) {
		this.boardTitle = data.getBoardTitle();
		this.boardContent = data.getBoardContent();
		this.boardCnt = 0;
		this.boardWriter = data.getBoardWriter();
		this.boardRegdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		this.likeCount = 0;
		this.boardMaincate = data.getBoardMaincate();
		this.subcategory = data.getSubcategory();
	}

	public void updateContent(BoardQnaDTO dto) {
		this.boardTitle = dto.getBoardTitle();
		this.boardContent = dto.getBoardContent();
	}

	public void updateReadCount(int boardCnt) {
		this.boardCnt = boardCnt;
	}

	public void addLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}






}