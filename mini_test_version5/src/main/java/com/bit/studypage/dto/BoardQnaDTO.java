package com.bit.studypage.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bit.studypage.entity.BoardQna;

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
	private List<Long> attachDelete; // 삭제할 파일 ID 목록
	private List<Long> addFile; //추가할 파일 목록 
	
	public int commentCount; // 댓글 수
	
	// CommentDTO 리스트 추가
	private List<CommentQnaDTO> comments;
	
	//Entity에서 DTO로 변환하는 코드
	public static BoardQnaDTO fromEntity(BoardQna entity) {
        BoardQnaDTO dto = new BoardQnaDTO();
        dto.setBoardId(entity.getBoardId());
        dto.setBoardTitle(entity.getBoardTitle());
        dto.setBoardContent(entity.getBoardContent());
        dto.setBoardWriter(entity.getBoardWriter());
        dto.setBoardRegdate(entity.getBoardRegdate());

        return dto;
    }
	
}

