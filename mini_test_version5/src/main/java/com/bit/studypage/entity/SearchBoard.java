package com.bit.studypage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "search_board2")
@Data
public class SearchBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long searchBoardId;
    @Column
    private String searchCate;

    @Column
    private String searchTitle;

    @Column
    private String searchWriter;

    @Column
    private LocalDate searchRegDate;

    @Column
    private int searchCnt;

    //검색 내용을 저장하기 위한 필드
    @Column
    private String searchContent;

    @Column(name = "comment_count")
    private Integer commentCount;




}
