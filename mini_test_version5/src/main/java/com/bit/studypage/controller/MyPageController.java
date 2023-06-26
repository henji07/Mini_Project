package com.bit.studypage.controller;

import com.bit.studypage.entity.ProfileImg;
import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.BoardRepository;
import com.bit.studypage.service.impl.BoardService;
import com.bit.studypage.service.impl.CommentsService;
import com.bit.studypage.service.impl.ProfileImgService;
import com.bit.studypage.service.impl.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@Slf4j
public class MyPageController {
    UsersService usersService;
    BoardService boardService;
    CommentsService commentsService;
    ProfileImgService profileImgService;


    @Autowired
    public MyPageController(UsersService usersService, BoardService boardService, CommentsService commentsService, ProfileImgService profileImgService) {
        this.usersService = usersService;
        this.boardService = boardService;
        this.commentsService = commentsService;
        this.profileImgService = profileImgService;
    }

    @GetMapping("/myPageView")
    public ModelAndView viewMyPage() {
        log.info("용순");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/myPage.html");
        Users users = usersService.loginUser(1L);
        mv.addObject("user", users);
        mv.addObject("boardList", boardService.getBoardList(users.getUserNickname()));
        mv.addObject("commList", commentsService.getCommentsList(users.getUserNickname()));
        mv.addObject("commentsList",commentsService.getCommentsList(users.getUserNickname()));
        return mv;
    }
    @PostMapping("/changeImg")
    public String changeImg(@RequestParam("image") MultipartFile image, Model model) throws IOException {
        System.out.println("용순이바부");
        if (!image.isEmpty()){
            ProfileImg profileImg = new ProfileImg();
            String origin = image.getOriginalFilename();
            Path path = Paths.get("C:\\profile").resolve(image.getOriginalFilename());
            profileImg.setOriginName(origin);
            profileImg.setPath(path.toString());
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            profileImgService.updateProfileImg(profileImg);
            model.addAttribute("profileImg", origin);
            System.out.println(origin+"111111111111");
        }
        return "redirect:/myPageView";
    }
}
