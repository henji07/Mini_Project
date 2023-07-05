package com.bit.studypage.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "comment_test")
public class Comment {
	
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
    
    @Builder
    public Comment(String content, long userId, long boardId) {
    	this.content = content;
    	this.userId = userId;
    	this.boardId = boardId;
    }

}
