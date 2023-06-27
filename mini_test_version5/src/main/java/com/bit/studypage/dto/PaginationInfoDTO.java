package com.bit.studypage.dto;

import lombok.Data;

@Data
public class PaginationInfoDTO {

	private int totalCount; //총 게시글 수
	private int currentPageNum; //현재 페이지 번호
	private int totalPageNum; //총 페이지 수
	private int pageSize; //한 페이지에 보여줄 게시글 수
	private int beginPageNum; //표시할 첫 페이지 번호
	private int endPageNum; //표시할 마지막 페이지 번호
	private int pageGroupSize; //한 번에 표시할 페이지 그룹의 크기
	
	public PaginationInfoDTO(int totalCount, int currentPageNum, int pageSize, int pageGroupSize) {
        this.totalCount = totalCount;
        this.currentPageNum = currentPageNum;
        this.pageSize = pageSize;
        this.pageGroupSize = pageGroupSize;
        this.totalPageNum = (int) Math.ceil((double) totalCount / pageSize);

        this.beginPageNum = ((currentPageNum - 1) / pageGroupSize) * pageGroupSize + 1;
        int tempEndPageNum = beginPageNum + pageGroupSize - 1;
        if (tempEndPageNum > totalPageNum) {
            this.endPageNum = totalPageNum;
        } else {
            this.endPageNum = tempEndPageNum;
        }
    }
}
