package com.bit.studypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {
	
	private Long id;
    private String fileName;
    private String fileType;
    private String filePath;

    //게시글에 대한 참조가 필요하다면, BoardQnaDTO 이용
    private BoardQnaDTO boardQna;
}
