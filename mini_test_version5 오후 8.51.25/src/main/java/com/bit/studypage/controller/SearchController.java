package com.bit.studypage.controller;

import com.bit.studypage.dto.ResponseDTO;
import com.bit.studypage.entity.SearchBoard;
import com.bit.studypage.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.text.AttributedString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;
    private AttributedString model;

    @GetMapping("/fullSearch")
    public String fullBoard(@RequestParam("keyword") String keyword, Model model,@PageableDefault(page = 0,size = 10, sort = "searchBoardId",direction = Sort.Direction.DESC) Pageable pageable) {
        // 호출된 SearchService의 searchboardlist() 메소드를 사용하여 검색 게시물 목록을 가져옴.
        Page<SearchBoard> searchBoard = searchService.searchBoardPagelist(pageable, keyword);
        // 검색 게시물 목록을 "boardList"라는 이름으로 Model에 추가.



        int nowPage = searchBoard.getNumber()+1;
        int startPage = Math.max(1, nowPage - 5);
        int endPage = Math.min(nowPage + 5 , searchBoard.getTotalPages());
        int totalPages = searchBoard.getTotalPages();  // 전체 페이지 수

        model.addAttribute("boardList", searchBoard);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("totalPages",totalPages);  // 모델에 추가

        // "/view/fullSearch" 뷰를 반환.
        return "/view/fullSearch";
    }

    @GetMapping("/board")
    public String searchBoardByTitle(@RequestParam("keyword") String searchKeyword, Model model) {
        // 검색어와 일치하는 게시물을 검색하기 위해 검색 서비스 호출
        List<SearchBoard> searchResults = searchService.searchBoardByTitleContent(searchKeyword);

        model.addAttribute("searchResults", searchResults);
        return "search-results";
    }


    @RequestMapping(value = "/view/fullSearch", method = RequestMethod.GET)
    public String fullSearchView() {
        // "/view/fullSearch" 뷰를 반환
        return "/view/fullSearch";
    }

    @PostMapping("/api/search")
    public ResponseEntity<?> searchPosts(@RequestParam("keyword") String keyword ) {
        System.out.println(keyword);
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        Map<String, Object> returnmap = new HashMap<String, Object>();
        try {

            List<SearchBoard> searchBoardList = searchService.searchBoardByTitleContent(keyword);
            returnmap.put("searchBoardList", searchBoardList);
            responseDTO.setItem(returnmap);
            responseDTO.setStatusCode(HttpStatus.OK.value());


            return ResponseEntity.ok().body(responseDTO);


        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);

        }

    }

    @PostMapping("/api/searchCT")
    public ResponseEntity<?> filterPostsByCategory(@RequestParam("category") String category) {
        System.out.println(category);
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        Map<String, Object> returnmap = new HashMap<String, Object>();
        try {

            List<SearchBoard> searchBoardList = searchService.searchBoardByCategory(category);
            if (searchBoardList.size() == 0) {
                searchBoardList = searchService.searchBoardlist();
                System.out.println(searchBoardList);
            }
            System.out.println(searchBoardList);
            returnmap.put("searchBoardList", searchBoardList);
            responseDTO.setItem(returnmap);
            responseDTO.setStatusCode(HttpStatus.OK.value());


            return ResponseEntity.ok().body(responseDTO);


        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);

        }

    }

    // 네비바 검색창
    @GetMapping("/api/searchMainBox")
    public String searchHeaderBox(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        System.out.println(keyword);
        List<SearchBoard> searchResults = searchService.searchBoardByTitleContent(keyword);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("keyword", keyword);
        return "view/fullSearch";
    }



    // 옵션에 따라 게시물을 검색하는 핸들러 메서드
    @PostMapping("/api/searchByOption")
    @ResponseBody
    public ResponseEntity<?> searchByOption(@RequestParam("option") String option,
                                            @RequestParam("keyword") String keyword) {
        List<SearchBoard> searchResults;

        // 옵션에 따라 검색 서비스의 메서드를 호출하여 검색 결과를 가져옴
        if ("A".equals(option)) {
            searchResults = searchService.searchBoardByTitleContent(keyword);
        } else if ("T".equals(option)) {
            searchResults = searchService.searchBoardByTitle(keyword);
        } else if ("C".equals(option)) {
            searchResults = searchService.searchBoardByContent(keyword);
        } else {
            // 유효하지 않은 검색 옵션일 경우, BadRequest 응답 반환
            return ResponseEntity.badRequest().body("유효하지 않은 검색 옵션입니다.");
        }

        // 검색 결과를 Map에 담아 응답으로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("results", searchResults);



        return ResponseEntity.ok(response);
    }
    @GetMapping("/api/searchByOption")
    public String searchOp(){

        return "view/fullSearch";
    }
    @GetMapping("/fullSearch/{searchBoardId}")
    public String searchDetailBoard(@PathVariable Long searchBoardId, Model model){

        System.out.println("여기서부터 시작!!!"+searchBoardId);

        SearchBoard searchBoard = searchService.searchDetailBoard(searchBoardId);
        model.addAttribute("searchBoard", searchBoard);


        return "view/fullSearch"; //여기 detail파일넣을꺼임
        // 상세 내용을 보여주는 별도의 뷰인 '{boardType}Detail'로 반환
//        return boardType + "Detail";
    }

}