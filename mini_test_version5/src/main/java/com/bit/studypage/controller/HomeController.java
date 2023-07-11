package com.bit.studypage.controller;

import com.bit.studypage.entity.SearchBoard;
import com.bit.studypage.repository.HomeRepository;

import com.bit.studypage.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	// HomeRepository를 의존성 주입하여 사용합니다.
	private final HomeService homeService;

	@Autowired
	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}

	@RequestMapping(value={"/","/main"})
	public String home(Model model ) {

		log.debug("hello");

		List<SearchBoard> top12Posts = homeService.getTop12Posts(); // 쿼리메소드 Cnt 기준 Top 12 DESC
		System.out.println("정병유신진"+top12Posts.get(0).getSearchCate());
		List<SearchBoard> top12PostsByCommentCount = homeService.getTop12PostsByCommentCount();//댓글


		model.addAttribute("top12Posts",top12Posts); // 모델에담음

		model.addAttribute("top12Posts", top12PostsByCommentCount); //댓글 모델에 담음
		Map<String, String> categoryImages = new HashMap<>();
		categoryImages.put("소통해요", "/image/sotong.png");
		categoryImages.put("궁금해요", "/image/curious.png");
		categoryImages.put("함께해요", "/image/study.png");
		categoryImages.put("프로젝트", "/image/project.png");

		model.addAttribute("categoryImages", categoryImages);

		return "/view/home"; //이 템플릿으로 간다.
	}
	@RequestMapping(value="/{category}")
	public String homeWithCategory(@PathVariable("category") String category, Model model) {

		System.out.println(category);
		log.debug("Selected Category: " + category);
		System.out.println(category + "= 카테고리");


		Map<String, String> categoryImages = new HashMap<>();
		categoryImages.put("소통해요", "/image/sotong.png");
		categoryImages.put("궁금해요", "/image/curious.png");
		categoryImages.put("함께해요", "/image/study.png");
		categoryImages.put("프로젝트", "/image/project.png");

		model.addAttribute("categoryImages", categoryImages);

		List<SearchBoard> top12PostsByCategory = homeService.getTop12PostsByCategory(category); // 쿼리메소드 Cnt 기준 Top 12 DESC

		System.out.println(top12PostsByCategory);
		model.addAttribute("top12Posts", top12PostsByCategory); // 모델에담음
		System.out.println();
		return "/view/home"; //이 템플릿으로 간다.
	}



}
