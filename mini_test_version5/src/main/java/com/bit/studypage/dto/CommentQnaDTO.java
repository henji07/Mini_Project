package com.bit.studypage.dto;

import java.time.LocalDateTime;

import com.bit.studypage.entity.CommentQna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentQnaDTO {
	private int id;
    private String content;
    private String writer;
    private LocalDateTime createdAt;

    public static CommentQnaDTO toDto(CommentQna comment, String userId) {
        return new CommentQnaDTO(
        		comment.getId(),
                comment.getContent(),
                userId,
                comment.getCreatedAt() 
        );
    }
}
