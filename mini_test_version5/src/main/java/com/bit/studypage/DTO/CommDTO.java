package com.bit.studypage.DTO;

import com.bit.studypage.entity.Comments;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommDTO {

    private Long commentId;
    private Long childKey;
    private Long postId;
    private String commentContent;
    private String commWriter;
    private LocalDateTime createdAt;
    private String postTitle;

    public CommDTO(Comments comments) {
        this.commentId = comments.getCommentId();
        this.childKey = comments.getChildKey();
        this.postId = comments.getPostId();
        this.commentContent = comments.getCommentContent();
        this.commWriter = comments.getCommWriter();
        this.createdAt = comments.getCreatedAt();
    }
    public CommDTO(){}
}