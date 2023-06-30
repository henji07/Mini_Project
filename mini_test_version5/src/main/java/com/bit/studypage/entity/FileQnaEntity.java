package com.bit.studypage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="FileTest")
@Getter
@NoArgsConstructor
public class FileQnaEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    private String filePath;
    
    @OneToOne
    @JoinColumn(name = "board_id")
    private BoardQna boardQna;
    
    public void setBoardQna(BoardQna boardQna) {
        this.boardQna = boardQna;
    }

    public FileQnaEntity(String fileName, String fileType, String filePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
    }
}
