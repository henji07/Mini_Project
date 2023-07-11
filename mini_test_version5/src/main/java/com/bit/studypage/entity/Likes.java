package com.bit.studypage.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "Likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @NotNull
    private Long usersId;

    @NotNull
    private Long postId;
    private String boardTitle;
    private Timestamp boardRegDate;
    private int boardViewCnt = 0;


}
