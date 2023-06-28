package com.bit.studypage.domain.entity;

import com.fasterxml.jackson.core.JsonToken;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
public class Board {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 10, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private String boardTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public Board(Long id, String author, String boardTitle, String content) {
        this.id = id;
        this.author = author;
        this.boardTitle = boardTitle;
        this.content = content;
    }
}
