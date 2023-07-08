package com.bit.studypage.dto;

import lombok.Data;

/**
 * QNA 게시판 댓글 DTO
 * @author 82103
 *
 */
@Data
public class BoardCmmntQnaDTO {
	
	/** 댓글ID */
	private int commentId;
	
	/** 댓글내용 */
    private String commentContent;
    
    /** 작성자ID */
    private long usersId;
    
    /** 작성자명 */
    private String userName;
    
    /** 작성일자 */
    private String createdAt;

}
