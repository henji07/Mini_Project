package com.bit.studypage.dto;

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
	
	private List<FileQnaDTO> fileList;
	private List<Long> attachDelete; // 삭제할 파일 ID 목록
	private List<Long> addFile; //추가할 파일 목록 
	
	public int commentCount; // 댓글 수
	
	private long likeCount;
	
	private List<BoardCmmntQnaDTO> comments;
	
	private boolean heart;
	
	//Entity에서 DTO로 변환하는 코드
	/*
	 * public static BoardQnaDTO fromEntity(BoardQna entity) { BoardQnaDTO dto = new
	 * BoardQnaDTO(); dto.setBoardId(entity.getBoardId());
	 * dto.setBoardTitle(entity.getBoardTitle());
	 * dto.setBoardContent(entity.getBoardContent());
	 * dto.setBoardWriter(entity.getBoardWriter());
	 * dto.setBoardRegdate(entity.getBoardRegdate()); return dto; }
	 */
	
}

