package com.bit.studypage.dto.board;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) //  null 값을 가지는 필드는, JSON 응답에 포함되지 않음
@Getter
@AllArgsConstructor
public class ResponseCommentDTO<T> {

    private String success;
    private String message;
    private T data;
    private List<T> dataList;

    // 기본 생성자 추가
    public ResponseCommentDTO() {
    }
}