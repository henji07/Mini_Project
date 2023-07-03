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
	
	private List<FileQnaDTO> fileList;
	private List<Long> toDeleteFileIds; // 삭제할 파일 ID 목록
	
	private int commentCount; // 댓글 수
	
	// CommentDTO 리스트 추가
	private List<CommentDTO> comments;
	
}

