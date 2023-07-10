package com.bit.studypage.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bit.studypage.dto.LikeQnaDTO;
import com.bit.studypage.entity.BoardQna;
import com.bit.studypage.entity.LikeQna;
import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.BoardQnaRepository;
import com.bit.studypage.repository.LikeQnaRepository;
import com.bit.studypage.repository.MemberRepository;
import com.bit.studypage.service.LikeQnaService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeQnaServiceImpl implements LikeQnaService {
	
	private final LikeQnaRepository likeRepository;
	private final BoardQnaRepository boardRepository;
	private final MemberRepository memberRepository;
    
    //사용자의 인증 정보 확인
    public Users validateAuthentication(Authentication authentication) {
    	// 인증 정보가 없거나 사용자가 인증되지 않았을 경우 예외 발생시킴
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 해주세요.");
        }

        // 인증된 사용자의 아이디를 가져옴
        String userId = authentication.getName();

        // 아이디를 이용해 사용자를 찾음. 사용자가 없을 경우 예외를 발생시킴
        Optional<Users> userOptional = memberRepository.findByUserId(userId);
        Users user = userOptional.orElseThrow(() -> new NoSuchElementException("No User found with username " + userId));

        // 인증된 사용자 반환
        return user;
    }

	//좋아요 누르기 
	@Override
	public LikeQnaDTO insertLike(LikeQnaDTO likeDTO, Authentication authentication) {
		
		System.out.println("서비스에 오나?");
		
		// 사용자의 인증 정보를 확인하는 메소드 호출
        Users user = validateAuthentication(authentication);
        
        // 게시판 번호로 게시글 찾기, 게시글이 없을 경우 예외 발생
        BoardQna board = boardRepository.findById(likeDTO.getBoardId()).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시판을 찾을 수 없습니다.");
        });
        
        // 이미 좋아요를 누른 사용자인지 확인
        Optional<LikeQna> existingLike = likeRepository.findByUserIdAndBoardId(user.getUsersId(), board.getBoardId());
        if (existingLike.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 좋아요를 누르셨습니다.");
        }

        // LikeQna 객체 생성
        LikeQna likeQna = LikeQna.builder()
                .userId(user.getUsersId())
                .userStringId(user.getUserId())
                .boardId(board.getBoardId())
                .boardTitle(board.getBoardTitle())
                .likeCount(0)
                .build();

        // 생성된 LikeQna 객체를 저장
        likeRepository.save(likeQna);
        
        // 게시글의 좋아요 수 증가
        board.addLikeCount(board.getLikeCount() + 1);
        
        System.out.println("좋아유 = "+board.getLikeCount());
        
        boardRepository.save(board);

        // 좋아요 수를 DTO에 추가
        long likeCount = board.getLikeCount();
        likeDTO.setLikeCount(likeCount);

        // DTO 객체로 변환하여 반환
        return LikeQnaDTO.fromEntity(likeQna,likeCount);
        
        
		        
	}

	//좋아요 취소 
	@Override
	public String removeLike(long boardId, Authentication authentication) {
        // 사용자의 인증 정보를 확인하는 메소드 호출
        Users user = validateAuthentication(authentication);

        // 좋아요 정보를 찾아 삭제, 정보가 없을 경우 예외 발생
        LikeQna likeQna = likeRepository.findByUserIdAndBoardId(user.getUsersId(), boardId)
        		.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "해당 게시글에 좋아요를 누르지 않았습니다."
                    ));

        likeRepository.delete(likeQna);
        
        // 게시글의 좋아요 수 감소
        BoardQna board = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시판을 찾을 수 없습니다.");
        });
        
        board.addLikeCount(board.getLikeCount() - 1);
        boardRepository.save(board);
        
        System.out.println("좋아유 취소 = "+board.getLikeCount());

        return String.format("좋아요 취소되었습니다.");
    }

	//좋아요 체크 여부 
	@Override
	public boolean isLikedByUser(long boardId, long userId) {
		return likeRepository.existsByUserIdAndBoardId(userId, boardId);
	}

}
