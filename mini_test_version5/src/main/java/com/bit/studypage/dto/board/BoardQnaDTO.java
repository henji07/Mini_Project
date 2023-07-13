package com.bit.studypage.dto.board;

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
	private String boardRegdate;
	private int boardCnt;
	private String userName;
	
	private String boardMaincate;//대분류 카테고리 
	
	private String subcategory;

	private List<FileQnaDTO> fileList;
	private List<Long> attachDelete; // 삭제할 파일 ID 목록
	private List<Long> addFile; //추가할 파일 목록

	public int commentCount; // 댓글 수

	private long likeCount;

	private List<BoardCmmntQnaDTO> comments;

	private boolean heart;

	//날짜 가져오기
	public void setBoardRegdate(String boardRegdate) {
	    this.boardRegdate = boardRegdate;
	}



}

