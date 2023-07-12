package com.bit.studypage.entity.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="FileTest")
@Getter
@ToString
@NoArgsConstructor
public class FileQna {

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
    public FileQna(String originalFileName, String fileType, String storeFileName, long boardId) {
        this.fileName = originalFileName;
        this.fileType = fileType;
        this.filePath = storeFileName;
        this.boardId = boardId;
    }


}
