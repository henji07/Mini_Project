package com.bit.studypage.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;
@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @NotNull
    private String boardWriter;

    @NotNull
    private String boardTitle;

    @NotNull
    private String boardCate;

    @NotNull
    private Timestamp boardRegDate;

    private int boardViewCnt = 0;

    @NotNull
    private String boardContent;
}
