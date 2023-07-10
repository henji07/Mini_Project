package com.bit.studypage.controller;

import com.bit.studypage.dto.ResponseDTO;
import com.bit.studypage.entity.SearchBoard;
import com.bit.studypage.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    private SearchService searchService;

    //검색어가 들어오면 검색버튼이 눌림
    @PostMapping("/api/searchMainBox")
    public String searchPostsMain(@RequestParam("keyword") String keyword, Model model) {
        System.out.println(keyword);
        List<SearchBoard> searchResults = searchService.searchBoardByTitleContent(keyword);
        System.out.println(searchResults);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("keyword", keyword);
        return "/fullSearch";  // fullSearch 페이지를 반환합니다.
    }


}