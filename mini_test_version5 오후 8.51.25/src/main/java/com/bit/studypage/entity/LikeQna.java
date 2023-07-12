package com.bit.studypage.entity;

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
@Table(name = "like_qna")
public class LikeQna {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="like_id")
	private long likeId;

	@Column(name="users_id")
    private long userId;

	@Column(name = "user_id")
    private String userStringId;

    @Column(name = "board_id")
    private long boardId;

    @Column(name = "board_title")
    private String boardTitle;

    @Column(name = "like_count")
    private long likeCount;

    @Builder
    public LikeQna(long userId, String userStringId, long boardId, String boardTitle, long likeCount) {
    	this.userId = userId;
    	this.userStringId = userStringId;
    	this.boardId = boardId;
    	this.boardTitle = boardTitle;
    	this.likeCount = likeCount;
    }
}
