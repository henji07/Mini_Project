package com.bit.studypage.controller;

import com.bit.studypage.entity.*;
import com.bit.studypage.entity.board.BoardQna;
import com.bit.studypage.service.impl.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.*;

@RestController
@Slf4j
public class MyPageController {
    UsersServiceImpl usersServiceImpl;
    BoardServiceImpl boardServiceImpl;
    CommentsServiceImpl commentsServiceImpl;
    ProfileImgServiceImpl profileImgServiceImpl;
    LikesServiceImpl likesServiceImpl;
    DelUsersServiceImpl delUsersService;

    PasswordEncoder passwordEncoder;
    Long loginId;

    @Autowired
    public MyPageController(UsersServiceImpl usersServiceImpl, BoardServiceImpl boardServiceImpl, CommentsServiceImpl commentsServiceImpl, ProfileImgServiceImpl profileImgServiceImpl, LikesServiceImpl likesServiceImpl, DelUsersServiceImpl delUsersService, PasswordEncoder passwordEncoder) {
        this.usersServiceImpl = usersServiceImpl;
        this.boardServiceImpl = boardServiceImpl;
        this.commentsServiceImpl = commentsServiceImpl;
        this.profileImgServiceImpl = profileImgServiceImpl;
        this.likesServiceImpl = likesServiceImpl;
        this.delUsersService = delUsersService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/1234")
    public ModelAndView ggg() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/maptest.html");
        return mv;
    }

    //처음 page가 load됐을 때 view에 model을 담기
    @GetMapping("/myPage")
    public ModelAndView viewMyPage(Principal principal, HttpSession session, @RequestParam(name = "page", defaultValue = "0") int page) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/myPage.html");
        loginId = usersServiceImpl.getUsersId(principal.getName());
        Users users = usersServiceImpl.loginUser(loginId);
        System.out.println(loginId + "로그인유저 아이디 확인!-!4");
        session.setAttribute("user", users);

        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "boardId");
        Page<BoardQna> boardPage = boardServiceImpl.pageList(users.getUserNickname(), pageable);
        mv.addObject("boards", boardPage);

        Pageable pageable2 = PageRequest.of(page, 10, Sort.Direction.DESC, "commentId");
        Page<Comments> commPage = commentsServiceImpl.getCommentsPage(pageable2, users.getUserNickname());
        mv.addObject("comments", commPage);

        Pageable pageable3 = PageRequest.of(page, 10, Sort.Direction.DESC, "likeId");
        Page<Likes> likePage = likesServiceImpl.likesPage(users.getUsersId(), pageable3);
        mv.addObject("likes", likePage);
        System.out.println(likePage + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

//        List<CommDTO> commList = new ArrayList<>();
//        for (Comments c : commentsServiceImpl.getCommentsList(users.getUserNickname())) {
//            CommDTO cdto = c.entityToDto();
//            cdto.setPostTitle(boardServiceImpl.getBoard(c.getPostId()).getBoardTitle());
//            commList.add(cdto);
//        }

        List<BoardQna> boardLike = boardServiceImpl.getBoardQnaList(likesServiceImpl.getLike(users.getUsersId()));

        String[] interest = usersServiceImpl.loginUser(loginId).getInterest().split(",");
        List<String> interestsList = Arrays.asList(interest);
        mv.addObject("interests", interestsList);
        mv.addObject("boardList", boardServiceImpl.getBoardQnaList(users.getUserNickname()));
        mv.addObject("commList", commentsServiceImpl.getCommentsList(users.getUserNickname()));
        if (profileImgServiceImpl.getProfileImg(loginId) != null) {
            mv.addObject("profileImg", profileImgServiceImpl.getProfileImg(loginId));
        } else {ProfileImg profileImg = new ProfileImg();
            profileImg.setOriginName("히히.jpg");
            profileImg.setUserId(loginId);
            profileImg.setPath("C:\\profile\\히히.jpg");
            profileImgServiceImpl.updateProfileImg(profileImg);
            mv.addObject("profileImg", profileImgServiceImpl.getProfileImg(loginId));
            System.out.println("히히발싸");
        }
        mv.addObject("interest", usersServiceImpl.loginUser(loginId).getInterest().split(","));
        mv.addObject("countBoard", boardServiceImpl.getCountBoardQna(users.getUserNickname()));
        mv.addObject("countComments", commentsServiceImpl.getCountComm(users.getUserNickname()));
        mv.addObject("commentsList", commentsServiceImpl.getCommList(users.getUserNickname()));
        mv.addObject("likeBoard", boardLike);
//        mv.addObject("likes",);
        log.info(boardServiceImpl.getCountBoardQna(users.getUserNickname()));
        log.info("시이발!2");
        return mv;
    }

    @GetMapping("/boards")
    public Page<BoardQna> getBoards(@RequestParam(name = "pageNum", defaultValue = "0") int page, HttpSession session) {
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "boardId");
        Users user = (Users) session.getAttribute("user");
        Page<BoardQna> boardPage = boardServiceImpl.pageList(user.getUserNickname(), pageable);
//        model.addAttribute("boards", boardPage);
        System.out.println(boardPage.getTotalElements());
        System.out.println(boardPage.getTotalPages());
        System.out.println(boardPage);
//        return "myPage :: #profile-historycontainer";
        return boardPage;
    }

    @GetMapping("/comm")
    public Page<Comments> getComms(@RequestParam(name = "pageNum", defaultValue = "0") int page, HttpSession session) {
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "commentId");
        Users user = (Users) session.getAttribute("user");
        Page<Comments> commPage = commentsServiceImpl.getCommentsPage(pageable, user.getUserNickname());
//        model.addAttribute("boards", boardPage);
        System.out.println(commPage.getTotalElements());
        System.out.println(commPage.getTotalPages());
        System.out.println(commPage);
//        return "myPage :: #profile-historycontainer";
        return commPage;
    }

    @GetMapping("/like")
    public Page<Likes> getLikes(@RequestParam(name = "pageNum", defaultValue = "0") int page, HttpSession session) {
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "likeId");
        Users user = (Users) session.getAttribute("user");
        Page<Likes> likePage = likesServiceImpl.likesPage(user.getUsersId(), pageable);
//        model.addAttribute("boards", boardPage);
        System.out.println(likePage.getTotalElements());
        System.out.println(likePage.getTotalPages());
        System.out.println(likePage);
//        return "myPage :: #profile-historycontainer";
        return likePage;
    }


    //    @PostMapping("/interest")
//    public ResponseEntity<?> setInterest(HttpSession session){
//        List<String> returnList = Arrays.asList(usersServiceImpl.loginUser(1L).getInterest().split(","));
//        log.info("111111111111111111111111111"+returnList.toString());
//        return ResponseEntity.ok().body(returnList);
//    }
    @PostMapping("/changeImg")
    public String changeImg(@RequestParam("image") MultipartFile image, Model model, HttpSession session) throws IOException {
        if (!image.isEmpty()) {
            ProfileImg profileImg = profileImgServiceImpl.getProfileImg(loginId);
            String origin = image.getOriginalFilename();
            Path path = Paths.get("C:\\profile").resolve(origin);
            profileImg.setOriginName(origin);
            System.out.println("이미지 변경 이름 : " + origin);
            profileImg.setPath(path.toString());
            Users user = (Users) session.getAttribute("user");
            profileImg.setUserId(user.getUsersId());
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            profileImgServiceImpl.updateProfileImg(profileImg);
            model.addAttribute("profileImg", origin);
        }
        return "redirect:/myPageView";
    }


    @PostMapping("/myPage/name-info")
    public void updateName(Users users, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        user.setName(users.getName());
        user.setUserNickname(users.getUserNickname());
        System.out.println(users.toString() + "유저닉네임");
        user.setInterest(users.getInterest());
        System.out.println(user.toString() + "xxxxxxxxxxxx");
        usersServiceImpl.updateUesr(user);
//        System.out.println(interest.toString()+"김도헌111"); // string 잘 들어옴
    }

    @PostMapping("/myPage/user-info")
    public void updateUserInfo(Users users, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        log.info(users.toString() + "wwwwwwwwwww");
        user.setUserId(users.getUserId());
        user.setEmail(users.getEmail());
        user.setPassword(users.getPassword());
        user.setGender(users.getGender());
        user.setPhone(users.getPhone());
        user.setIsTerms(users.getIsTerms());
        usersServiceImpl.updateUesr(user);
    }

    @PostMapping("/myPage/check-pass")
    public String checkPass(String pass, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        log.info("비번체크" + pass);
        log.info("tldlqkf" + user.getPassword());
        if (passwordEncoder.matches(pass, user.getPassword())) {
            log.info("ok리턴");
            return "ok";
        } else return "";
    }

    @DeleteMapping("/myPage/del-user")
    public void delUser(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        DelUsers delUsers = new DelUsers(user);
        log.info("삭제유저 추가" + delUsers.toString());
        delUsersService.setDelUser(delUsers);
        log.info("유저 삭제한당" + user);
        usersServiceImpl.delUser(user);
    }

    @DeleteMapping("/del-board")
    public void delBoard(@RequestParam("board-check") List<Long> board) {
        System.out.println("보드 불러온거" + board);
        for (Long i : board) {
            boardServiceImpl.delBoardQna(i);
        }
        System.out.println("보드삭제" + board);
    }

    @DeleteMapping("/del-comm")
    public void delComm(@RequestParam("board-check") List<Long> comm) {
        for (Long i : comm) {
            commentsServiceImpl.delComm(i);
        }
        System.out.println("댓글삭제");
    }

    @DeleteMapping("/del-like")
    public void delLike(@RequestParam("board-check") List<Long> like) {
        for (Long i : like) {
            likesServiceImpl.delLike(i);
        }
        System.out.println("좋아요삭제");
    }
}
