package com.bit.studypage.dto;

import com.bit.studypage.entity.LikeQna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeQnaDTO {
	
	private long likeId;
	
    private long userId;
	
    private String userStringId;

    private long boardId;
    
    private String boardTitle;

    private long likeCount;
    
	public static LikeQnaDTO fromEntity(LikeQna likeQna, long likeCount) {
		LikeQnaDTO likeQnaDTO = new LikeQnaDTO();
        likeQnaDTO.setUserId(likeQna.getUserId());
        likeQnaDTO.setUserStringId(likeQna.getUserStringId());
        likeQnaDTO.setBoardId(likeQna.getBoardId());
        likeQnaDTO.setBoardTitle(likeQna.getBoardTitle());
        likeQnaDTO.setLikeCount(likeCount);
        return likeQnaDTO;
	}

}
