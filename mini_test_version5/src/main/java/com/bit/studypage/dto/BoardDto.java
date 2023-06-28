package com.bit.studypage.dto;

import com.bit.studypage.domain.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String author;
    private String boardtitle;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .author(author)
                .boardTitle(boardtitle)
                .content(content)
                .build();
        return build;
    }

    @Builder
    public BoardDto(Long id, String author, String boardTitle, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.author = author;
        this.boardtitle = boardTitle;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
