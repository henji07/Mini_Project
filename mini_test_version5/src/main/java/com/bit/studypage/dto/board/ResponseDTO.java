package com.bit.studypage.dto.board;

import java.util.List;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private List<T> items;
    private T item;
    private String errorMessage;
    private int statusCode;

}


