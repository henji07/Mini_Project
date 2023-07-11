package com.bit.studypage.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bit.studypage.dto.BoardQnaDTO;
import com.bit.studypage.dto.CommentQnaDTO;
import com.bit.studypage.dto.LikeQnaDTO;

import com.bit.studypage.dto.ResponseDTO;
import com.bit.studypage.entity.Users;
import com.bit.studypage.service.BoardQnaService;
import com.bit.studypage.service.LikeQnaService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/board")
public class BoardQnaController {

    private final BoardQnaService boardService;
    private final LikeQnaService likeService;



    //글 등록 화면으로 이동
    @GetMapping("/insert-board-view")
    public ModelAndView insertBoardView(Authentication authentication) {
        ModelAndView mv = new ModelAndView();

        String userName = null;
        if(ObjectUtils.isNotEmpty(authentication)) {
            if (authentication.getPrincipal() instanceof Users) {
                Users user = (Users) authentication.getPrincipal();
                userName = user.getUserId();
            }
        }
        mv.addObject("userName", userName);


        mv.setViewName("view/boardInsertQna.html");
        return mv;
    }

    //글 목록 화면으로 이동
    @GetMapping("/qnaPage/{pageNum}")
    public ModelAndView getBoardList(@PathVariable("pageNum") int pageNum,
                                     @RequestParam(required = false) String sortOption) {


        ModelAndView mv = new ModelAndView();

        //게시글 목록을 가져옴
        List<BoardQnaDTO> boardList = boardService.getBoardList(pageNum, sortOption);

        mv.addObject("qnaList", boardList);
        mv.addObject("currentPage", pageNum);
        mv.addObject("totalPages", boardService.getTotalPages(sortOption));
        mv.addObject("sortOption", sortOption);


        mv.setViewName("view/boardQna.html");

        return mv;

    }

    //검색 결과 화면으로 이동
    //required = false로 설정되면 해당 파라미터가 필수가 아니라는 것
    //파라미터가 누락되더라도 예외가 발생하지 않고 기본값으로 null이 할당
    @GetMapping("/search/{pageNum}")
    public ModelAndView getSearchList(@RequestParam(required = false) String keyword, @PathVariable int pageNum, String sortOption) {

        ModelAndView mv = new ModelAndView();

        List<BoardQnaDTO> searchList = boardService.searchBoardsByTitle(keyword, pageNum, sortOption);

        mv.addObject("searchList", searchList);
        mv.addObject("keyword", keyword);
        mv.addObject("currentPage", pageNum);
        mv.addObject("totalPages", boardService.getSearchTotalPages(keyword));
        mv.addObject("sortOption", sortOption);

        if (searchList.isEmpty()) {
            mv.addObject("noResultMessage", "검색 결과가 없습니다.");
        }

        mv.setViewName("view/boardSearchQna.html");

        return mv;
    }


    //글 등록 -> ajax
    @PostMapping("/board-insert")
    public ResponseEntity<?> insertBoard(@RequestParam("uploadFiles") List<MultipartFile> files, BoardQnaDTO boardDTO,
                                         Authentication authentication) {

        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();

        String userId = null;

        if(ObjectUtils.isNotEmpty(authentication)) {
            if (authentication.getPrincipal() instanceof Users) {
                Users user = (Users) authentication.getPrincipal();
                userId = user.getUserId();
            }
        }

        try {
            //서비스 호출
            boardService.insertBoard(boardDTO, files, userId);

            Map<String, String> returnMap = new HashMap<String, String>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");

            responseDTO.setItem(returnMap);

            System.out.println("메시지"+responseDTO);

            return ResponseEntity.ok().body(responseDTO);

        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    //서버에 저장되어 있는 거 읽어오기
    @Value("${file.path}")
    private String fileUploadDir;


    @GetMapping("/attach/{filename:.+}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String filename) throws IOException {
        System.out.println("===========================");
        log.info("getImage");
        System.out.println("filename = " + filename);
        System.out.println("===========================");
        Path file = Paths.get(fileUploadDir, filename);

        InputStreamResource resource = new InputStreamResource(Files.newInputStream(file));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(file)))
                .body(resource);
    }



    //게시 글 및 댓글 상세 조회
    @GetMapping("/board/{boardId}")
    public ModelAndView getBoard(@PathVariable long boardId, Authentication authentication) {
        ModelAndView mv = new ModelAndView();

        long userId = 0;

        if(ObjectUtils.isNotEmpty(authentication)) {
            if (authentication.getPrincipal() instanceof Users) {
                Users user = (Users) authentication.getPrincipal();
                userId = user.getUsersId();
            }
        }

        //게시물 조회
        BoardQnaDTO dto = boardService.getBoardDetail(boardId, userId);


        mv.addObject("board", dto);

        // 로그인한 사용자 정보 전달
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            mv.addObject("username", username);
            mv.addObject("userId", userId);
        }


        mv.setViewName("/view/boardDetailQna");

        return mv;
    }

    //글 수정 화면으로 이동
    @GetMapping("/modify-board-view/{boardId}")
    public ModelAndView modifyBoardView(@PathVariable("boardId") long boardId) {

        System.out.println("boardId = " + boardId);

        ModelAndView mv = new ModelAndView();

        //게시물 조회
        BoardQnaDTO dto = boardService.getBoardDetail(boardId, 0);


        mv.addObject("board", dto);
        mv.setViewName("view/boardModifyQnA.html");
        return mv;
    }

    //글 수정
    @PostMapping("/board-modify")
    public ResponseEntity<?> updateBoard(@RequestPart(value = "board", required = false) BoardQnaDTO boardDTO,
                                         @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();
        try {
            // 서비스에서 게시글 수정 로직 실행

            boardService.updateBoard(boardDTO, files);

            // 클라이언트에게 전달할 응답 메시지 맵 생성
            Map<String, String> returnMap = new HashMap<String, String>();

            // 수정이 정상적으로 되었음을 알리는 메시지
            returnMap.put("msg", "정상적으로 수정되었습니다.");

            // 응답 DTO에 메시지 맵을 설정
            responseDTO.setItem(returnMap);

            // 정상적인 응답 반환
            return ResponseEntity.ok().body(responseDTO);

        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    //글 삭제
    @DeleteMapping ("/board")
    public ResponseEntity<?> deleteBoard(BoardQnaDTO boardDTO) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();
        try {
            //Long으로 보내기 때문에 엔티티 안 만들어도 됨.
            boardService.deleteBoard(boardDTO.getBoardId());

            Map<String, String> returnMap = new HashMap<String, String>();

            returnMap.put("msg", "정상적으로 삭제되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    //시큐리티 권한 처리 - 글쓰기 화면에서 로그인한 사용자의 아이디를 받아옴
    @GetMapping("/api/user")
    public ResponseEntity<String> getLoggedInUser(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }

    //이름 값에서 아이디 값 뽑기 - 좋아요 여부 체크할 때 쓰는 api
    @GetMapping("/api/user/id")
    public ResponseEntity<Long> getLoggedInUserId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Users) {
            Users user = (Users) authentication.getPrincipal();
            Long userId = user.getUsersId();

            System.out.println("userId=" + userId);
            return ResponseEntity.ok(userId);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // or your error handling
    }

    //좋아요
    @PostMapping("/like-insert/{boardId}")
    public ResponseEntity<?> insert(@PathVariable("boardId") long boardId,
                                    @RequestBody @Valid LikeQnaDTO likeDTO, Authentication authentication) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();

        System.out.println("컨트롤러에 오나?");

        //서비스 호출
        likeService.insertLike(likeDTO, authentication);
        Map<String, String> returnMap = new HashMap<String, String>();

        returnMap.put("msg", "좋아요 반영되었습니다.");

        responseDTO.setItem(returnMap);

        System.out.println("리스폰스디티오="+responseDTO);

        return ResponseEntity.ok().body(responseDTO);

    }

    //좋아요 취소
    @DeleteMapping("/like-delete/{boardId}")
    public ResponseEntity<?> delete(@PathVariable("boardId") long boardId,
                                    Authentication authentication) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();

        //서비스 호출
        likeService.removeLike(boardId, authentication);

        Map<String, String> returnMap = new HashMap<String, String>();

        returnMap.put("msg", "좋아요 취소되었습니다.");

        responseDTO.setItem(returnMap);

        return ResponseEntity.ok().body(responseDTO);

    }

    //좋아요 체크 여부
    @GetMapping("/check-like/{boardId}/{userId}")
    public ResponseEntity<?> checkLike(@PathVariable("boardId") long boardId, @PathVariable("userId") long userId) {
        System.out.println("체크 컨트롤러");
        // 해당 게시글에 대한 사용자의 좋아요 상태를 확인
        boolean isLiked = likeService.isLikedByUser(boardId, userId);

        System.out.println("좋아요 누르셨나요" + isLiked);

        // 좋아요 상태를 JSON 형태로 반환
        return ResponseEntity.ok().body(isLiked);
    }



}
