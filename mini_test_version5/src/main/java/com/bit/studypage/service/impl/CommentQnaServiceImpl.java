package com.bit.studypage.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bit.studypage.dto.CommentQnaDTO;
import com.bit.studypage.entity.BoardQna;
import com.bit.studypage.entity.CommentQna;
import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.BoardQnaRepository;
import com.bit.studypage.repository.CommentQnaRepository;
import com.bit.studypage.repository.MemberRepository;
import com.bit.studypage.repository.UsersRepository;
import com.bit.studypage.service.CommentQnaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentQnaServiceImpl implements CommentQnaService {
	
	private final CommentQnaRepository commentRepository;
    private final BoardQnaRepository boardRepository;
    private final UsersRepository userRepository;
    private final MemberRepository memberRepository;
    
    //사용자의 인증 정보 확인
    public Users validateAuthentication(Authentication authentication) {
    	// 인증 정보가 없거나 사용자가 인증되지 않았을 경우 예외 발생시킴
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 해주세요.");
        }

        // 사용자의 아이디를 가져옴
        String userId = authentication.getName();

        // 아이디를 이용해 사용자를 찾음. 사용자가 없을 경우 예외를 발생시킴
        Optional<Users> userOptional = memberRepository.findByUserId(userId);
        Users user = userOptional.orElseThrow(() -> new NoSuchElementException("No User found with username " + userId));

        // 인증된 사용자 반환
        return user;
    }

    // 댓글 작성하기
    @Transactional
    public CommentQnaDTO writeComment(long boardId, CommentQnaDTO commentDto, Authentication authentication) {
    	
    	// 사용자의 인증 정보를 확인하는 메소드 호출
    	Users user = validateAuthentication(authentication);
    	
    	
    	// 게시판 번호로 게시글 찾기, 게시글이 없을 경우 예외 발생
        BoardQna board = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없습니다.");
        });

        //Builder 패턴을 사용해 CommentQna 객체 생성
        // Builder 패턴을 사용하면 생성자에 많은 인자를 전달하는 것을 피하고, 객체 생성을 보다 명확하게 표현할 수 있다.
        CommentQna comment = CommentQna.builder() 
                .content(commentDto.getContent())//사용자가 입력한 댓글 내용을 가져옴
                .userId(user.getUsersId())//현재 인증된 사용자의 ID를 가져옴
                .userStringId(user.getUserId())//현재 인증된 사용자의 문자열 형식의 ID를 가져옴
                .boardId(board.getBoardId())//댓글이 달릴 게시글의 ID를 가져옴
                .boardTitle(board.getBoardTitle())//댓글이 달릴 게시글의 제목을 가져옴
                .createdAt(LocalDateTime.now())//댓글이 작성된 현재 시각을 가져옴
                .build();

        // 생성된 CommentQna 객체를 저장
        commentRepository.save(comment);

        // DTO 객체로 변환하여 반환
        return CommentQnaDTO.toDto(comment, user.getUserId());         
    }
    
    // 글에 해당하는 전체 댓글 불러오기
    @Transactional(readOnly = true)
    public List<CommentQnaDTO> getComments(long boardId) {
    	
    	// 게시글에 달린 모든 댓글을 가져옴
        List<CommentQna> comments = commentRepository.findAllByBoardId(boardId);
        
        // DTO로 변환하여 저장할 리스트를 생성
        List<CommentQnaDTO> commentDtos = new ArrayList<>();

        // 각 댓글에 대해
        comments.forEach(s -> {
        	// 댓글 작성자를 찾음. 작성자가 없을 경우 예외 발생
            Users user = userRepository.findById(s.getUserId()).orElseThrow(() -> {
                return new IllegalArgumentException("사용자를 찾을 수 없습니다.");
            });
            
            // 댓글을 DTO로 변환하여 리스트에 추가
            commentDtos.add(CommentQnaDTO.toDto(s, user.getUserId())); // user의 이름을 함께 전달
        });
        
        // 댓글 DTO 리스트를 반환
        return commentDtos;
    }
    
    // 댓글 삭제하기
    @Transactional
    public String deleteComment(int commentId) {
        CommentQna comment = commentRepository.findById(commentId).orElseThrow(()-> {
            return new IllegalArgumentException("댓글 Id를 찾을 수 없습니다.");
        });
        commentRepository.deleteById(commentId);
        return "삭제 완료";
    }
    

}
