package com.bit.studypage.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="FileTest")
@Getter
@NoArgsConstructor
public class FileQnaEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "board_id")
    private long boardId;

    @Builder
    public FileQnaEntity(String originalFileName, String fileType, String storeFileName, long boardId) {
        this.fileName = originalFileName;
        this.fileType = fileType;
        this.filePath = storeFileName;
        this.boardId = boardId;
    }





}