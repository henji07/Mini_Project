package com.bit.studypage.dto;

import com.bit.studypage.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//test용 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
	private int id;
    private String content;
    private String writer;

    public static CommentDTO toDto(Comment comment, String userName) {
        return new CommentDTO(
        		comment.getId(),
                comment.getContent(),
                userName
        );
    }
}
