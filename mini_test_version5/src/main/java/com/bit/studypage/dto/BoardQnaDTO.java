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
	
	public Long boardId;
	public String boardTitle;
	public String boardContent;
	public String boardWriter;
	public LocalDateTime boardRegdate;
	public int boardCnt;
	
	public List<FileQnaDTO> fileList;
	public List<Long> toDeleteFileIds; // 삭제할 파일 ID 목록
	
	public int commentCount; // 댓글 수
	
	// CommentDTO 리스트 추가
	public List<CommentDTO> comments;
	
}

