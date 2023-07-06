package com.bit.studypage.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "comment_qna")
public class CommentQna {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "comment_id")
    private int id;

    @Column(nullable = false, name="comment_content")
    private String content;

    @Column(name="users_id")
    private long userId;

    @Column(name = "board_id")
    private long boardId;
    
    @Column(name = "user_id")
    private String userStringId; 
    
    @Column(name = "board_title")
    private String boardTitle;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt; 
    
    @Builder
    public CommentQna(String content, long userId, long boardId, 
    				  String userStringId, String boardTitle, LocalDateTime createdAt) {
    	this.content = content;
    	this.userId = userId;
    	this.boardId = boardId;
    	this.userStringId = userStringId;
    	this.boardTitle = boardTitle;
    	this.createdAt = createdAt;
    }

}
