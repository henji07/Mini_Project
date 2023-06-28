package com.bit.studypage.controller;

import com.bit.studypage.DTO.CommDTO;
import com.bit.studypage.DTO.ResponseDTO;
import com.bit.studypage.entity.Board;
import com.bit.studypage.entity.Comments;
import com.bit.studypage.entity.ProfileImg;
import com.bit.studypage.entity.Users;
import com.bit.studypage.service.impl.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@Slf4j
public class MyPageController {
    UsersServiceImpl usersServiceImpl;
    BoardServiceImpl boardServiceImpl;
    CommentsServiceImpl commentsServiceImpl;
    ProfileImgServiceImpl profileImgServiceImpl;
    LikesServiceImpl likesServiceImpl;


    @Autowired
    public MyPageController(UsersServiceImpl usersServiceImpl, BoardServiceImpl boardServiceImpl, CommentsServiceImpl commentsServiceImpl, ProfileImgServiceImpl profileImgServiceImpl, LikesServiceImpl likesServiceImpl) {
        this.usersServiceImpl = usersServiceImpl;
        this.boardServiceImpl = boardServiceImpl;
        this.commentsServiceImpl = commentsServiceImpl;
        this.profileImgServiceImpl = profileImgServiceImpl;
        this.likesServiceImpl = likesServiceImpl;
    }
//처음 page가 load됐을 때 view에 model을 담기
    @GetMapping("/myPageView")
    public ModelAndView viewMyPage(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        List<CommDTO> commList = new ArrayList<>();
        mv.setViewName("view/myPage.html");
        Users users = usersServiceImpl.loginUser(1L);
        session.setAttribute("user", users);

        for(Comments c : commentsServiceImpl.getCommentsList(users.getUserNickname())){
            CommDTO cdto = c.entityToDto();
            cdto.setPostTitle(boardServiceImpl.getBoard(c.getPostId()).getBoardTitle());
            commList.add(cdto);
        }

        List<Board> boardLike = boardServiceImpl.getBoardList(likesServiceImpl.getLike(users.getUsersId()));

        String[] interest = usersServiceImpl.loginUser(1L).getInterest().split(",");
        List<String> interestsList = Arrays.asList(interest);
        mv.addObject("interests", interestsList);
        mv.addObject("boardList", boardServiceImpl.getBoardList(users.getUserNickname()));
        mv.addObject("commList", commentsServiceImpl.getCommentsList(users.getUserNickname()));
        mv.addObject("profileImg", profileImgServiceImpl.getProfileImg(1L));
        mv.addObject("interest",usersServiceImpl.loginUser(1L).getInterest().split(","));
        mv.addObject("countBoard",boardServiceImpl.getCountBoard(users.getUserNickname()));
        mv.addObject("countComments",commentsServiceImpl.getCountComm(users.getUserNickname()));
        mv.addObject("commentsList",commList);
        mv.addObject("likeBoard",boardLike);
//        mv.addObject("likes",);
        log.info(boardServiceImpl.getCountBoard(users.getUserNickname()));
        return mv;
    }
//    @PostMapping("/interest")
//    public ResponseEntity<?> setInterest(HttpSession session){
//        List<String> returnList = Arrays.asList(usersServiceImpl.loginUser(1L).getInterest().split(","));
//        log.info("111111111111111111111111111"+returnList.toString());
//        return ResponseEntity.ok().body(returnList);
//    }
    @PostMapping("/changeImg")
    public String changeImg(@RequestParam("image") MultipartFile image, Model model, HttpSession session) throws IOException {
        if (!image.isEmpty()){
            ProfileImg profileImg = new ProfileImg();
            String origin = image.getOriginalFilename();
            Path path = Paths.get("C:\\profile").resolve(image.getOriginalFilename());
            profileImg.setOriginName(origin);
            profileImg.setPath(path.toString());
            Users user = (Users)session.getAttribute("user");
            profileImg.setUserId(user.getUsersId());
            profileImg.setImgId(1L);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            profileImgServiceImpl.updateProfileImg(profileImg);
            model.addAttribute("profileImg", origin);
        }
        return "redirect:/myPageView";
    }


    @PostMapping("/myPage/name-info")
    public String updateName(@RequestParam String name, @RequestParam String nickname, @RequestParam List<String> interest){
        System.out.println(interest.toString()+"김도헌111"); // string 잘 들어옴
        return "";
    }
}
