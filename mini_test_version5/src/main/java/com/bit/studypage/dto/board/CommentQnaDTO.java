package com.bit.studypage.dto.board;

import java.time.LocalDateTime;

import com.bit.studypage.entity.board.CommentQna;

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

    public static CommentQnaDTO toDto(CommentQna comment, String userId) {
        return new CommentQnaDTO(
        		comment.getId(),
                comment.getContent(),
                userId
        );
    }
}
