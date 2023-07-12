package com.bit.studypage.dto;

<<<<<<< HEAD
import lombok.Data;

import java.util.List;

=======
import java.util.List;

import lombok.Data;

>>>>>>> origin/newyoojin
@Data
public class ResponseDTO<T> {
    private List<T> items;
    private T item;
    private String errorMessage;
    private int statusCode;
}
<<<<<<< HEAD
=======

>>>>>>> origin/newyoojin
