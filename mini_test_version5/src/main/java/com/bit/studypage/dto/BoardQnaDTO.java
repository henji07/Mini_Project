package com.bit.studypage.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BoardQnaDTO {
	
	private Long boardId;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private LocalDateTime boardRegdate;
	private int boardCnt;
	
	private com.bit.studypage.dto.FileQnaDTO file;
	
	private int commentCount; // 댓글 수
	
	// CommentDTO 리스트 추가
    private List<com.bit.studypage.dto.CommentDTO> comments;
	
}
