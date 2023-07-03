package com.bit.studypage.entity;

import java.time.LocalDateTime;

import com.bit.studypage.dto.BoardDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="board_id")
	private Long boardId;
	
	@Column(name="board_title")
	private String boardTitle;
	
	@Column(name="board_content")
	private String boardContent;
	
	@Column(name="board_view_cnt", nullable = false)
    private int boardCnt;
	
	@Column(name="board_writer")
	private String boardWriter;
	
	@Column(name="board_reg_date")
	private LocalDateTime boardRegdate;
	
	@Builder
	public Board(BoardDTO data) {
		this.boardTitle = data.getBoardTitle();
		this.boardContent = data.getBoardContent();
		this.boardCnt = 0;
		this.boardWriter = data.getBoardWriter();
		this.boardRegdate = LocalDateTime.now();
	}

	public void updateContent(BoardDTO dto) {
		this.boardTitle = dto.getBoardTitle();
		this.boardContent = dto.getBoardContent();
	}
	
	public void updateReadCount(int boardCnt) {
		this.boardCnt = boardCnt;
	}
	

}
