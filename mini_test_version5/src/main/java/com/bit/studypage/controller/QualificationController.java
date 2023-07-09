package com.bit.studypage.controller;

import com.bit.studypage.entity.Qualification;
import com.bit.studypage.pojo.ResponseData;
import com.bit.studypage.service.ApiService;
import com.bit.studypage.service.QualificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QualificationController {
    QualificationService qualificationService;
    ApiService apiService;

    QualificationController(QualificationService qualificationService, ApiService apiService) {
        this.qualificationService = qualificationService;
        this.apiService = apiService;
    }

    @PostMapping("/select/qua")
    public List<String> getSearch(@RequestParam("search") String getKey) {
//        List<String> get = new ArrayList<>();
        System.out.println("검색어" + getKey);
        System.out.println(qualificationService.findKey(getKey));
        return qualificationService.findKey(getKey);
    }

//    @GetMapping("/certificatePage")
//    public ModelAndView search(@PageableDefault(page = 0, size = 30) Pageable pageable) {
//        ModelAndView mv = new ModelAndView();
//
//        Page<Qualification> qualificationList = qualificationService.getQuaList(pageable);
//
//        int page = qualificationList.getNumber();
//        if(page == 0){
//            page = 1;
//        }
//        double startPage = Math.floor((page/10)*10);
//        double endPage = Math.min(startPage + 10,qualificationList.getTotalPages());
//
//        mv.addObject("List",qualificationList );
//        mv.addObject("startPage",startPage);
//        mv.addObject("endPage",endPage);
//
//        System.out.println("여기다!" + qualificationService.getQuaList(pageable));
////        return qualificationService.getSearchQuaList(name,pageable);
//        System.out.println(pageable.getPageNumber());
//        mv.setViewName("/view/boardCertificate.html");
//        return mv;
//    }

    @GetMapping("/certificatePage")
    public ModelAndView search2(@PageableDefault(page = 0, size = 30) Pageable pageable, @RequestParam(value = "name",defaultValue = "") String name) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/view/boardCertificate.html");
        System.out.println("컨트롤러 들어옴"+name);
        Page<Qualification> qualificationList;
        if (name.equals("") || name.isEmpty()) {
            mv.addObject("List", qualificationService.getQuaList(pageable));
            qualificationList = qualificationService.getQuaList(pageable);
            mv.addObject("name","");
        } else {
            mv.addObject("List", qualificationService.getSearchQuaList(name, pageable));
            qualificationList = qualificationService.getSearchQuaList(name, pageable);
            mv.addObject("name",name);
            System.out.println("여기다!" + qualificationService.getSearchQuaList(name, pageable));
//        return qualificationService.getSearchQuaList(name,pageable);
        }


        int page = qualificationList.getNumber();
        if(page == 0){
            page = 1;
        }
        double startPage = Math.floor((page/10)*10);
        double endPage = Math.min(startPage + 10,qualificationList.getTotalPages());
        System.out.println(startPage+"      /      "+endPage);
        mv.addObject("startPage",startPage);
        mv.addObject("endPage",endPage);
        System.out.println("여기까진 됨?");
        return mv;
    }

    @GetMapping("/select/qua/{name}")
    public ModelAndView select(@PathVariable String name) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/view/quaDetailes.html");
        mv.addObject("quaInfo",apiService.test(name));
        mv.addObject("name",name);
        System.out.println(apiService.test(name)+"오브젝트 넘어가는 거");
        String jsonData = apiService.test(name);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData responseData = objectMapper.readValue(jsonData, ResponseData.class);
        mv.addObject("quaInfo2", responseData);
        System.out.println("파싱: "+jsonData);
        return mv;
    }
}
