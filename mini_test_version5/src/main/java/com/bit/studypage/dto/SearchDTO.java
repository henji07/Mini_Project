package com.bit.studypage.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SearchDTO {

    private String keyword;  // 키워드

    private String cate;  // 카테고리 옵션 (A, T, C 등)

    private String categorykeyword;  // 카테고리별 키워드

    // 새로운 필드 추가
    private String searchOption;  // 검색 옵션 (제목+내용, 제목, 내용 등)

    // 새로운 필드 추가
    private String searchText;  // 검색 텍스트

    // 생성자, 게터, 세터, toString() 등 필요한 메소드 추가
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    // toString() 메소드
    @Override
    public String toString() {
        return "SearchDTO{" +
                "keyword='" + keyword + '\'' +
                ", cate='" + cate + '\'' +
                ", categorykeyword='" + categorykeyword + '\'' +
                ", searchOption='" + searchOption + '\'' +
                ", searchText='" + searchText + '\'' +
                '}';
    }
}
