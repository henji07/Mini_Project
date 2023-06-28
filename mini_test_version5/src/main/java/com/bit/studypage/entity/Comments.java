package com.bit.studypage.entity;

import com.bit.studypage.DTO.CommDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "child_key")
    private Long childKey;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "comment_content", nullable = false, columnDefinition = "TEXT")
    private String commentContent;

    @Column(name = "comm_writer", length = 50, nullable = false)
    private String commWriter;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public CommDTO entityToDto() {
        CommDTO dto = new CommDTO();
        dto.setCommentId(this.commentId);
        dto.setChildKey(this.childKey);
        dto.setPostId(this.postId);
        dto.setCommentContent(this.commentContent);
        dto.setCommWriter(this.commWriter);
        dto.setCreatedAt(this.createdAt);
        return dto;
    }
}
