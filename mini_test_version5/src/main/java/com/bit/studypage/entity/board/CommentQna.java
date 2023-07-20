package com.bit.studypage.entity.board;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bit.studypage.dto.board.CommentQnaDTO;

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
    private String createdAt;

    @Builder
    public CommentQna(String content, long userId, long boardId,
    				  String userStringId, String boardTitle) {
    	this.content = content;
    	this.userId = userId;
    	this.boardId = boardId;
    	this.userStringId = userStringId;
    	this.boardTitle = boardTitle;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
