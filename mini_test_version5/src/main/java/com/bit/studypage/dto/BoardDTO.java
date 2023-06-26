package com.bit.studypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
	
	private Long boardId;
	private String boardCate;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String boardRegDate;
	private int boardCnt;
}
