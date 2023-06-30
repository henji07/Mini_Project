package com.bit.studypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileQnaDTO {
	
	private Long id;
    private String fileName;
    private String fileType;
    private String filePath;

    //게시글에 대한 참조가 필요하다면, BoardQnaDTO 이용
    private com.bit.studypage.dto.BoardQnaDTO boardQna;
}
