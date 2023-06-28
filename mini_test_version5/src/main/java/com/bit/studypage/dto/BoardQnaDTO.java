package com.bit.studypage.dto;

import java.time.LocalDateTime;

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

}
