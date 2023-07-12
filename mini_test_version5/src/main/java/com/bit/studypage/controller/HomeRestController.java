package com.bit.studypage.controller;

import com.bit.studypage.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {
    @Autowired
    private HomeService homeService;
}
