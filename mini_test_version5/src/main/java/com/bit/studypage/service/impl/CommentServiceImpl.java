package com.bit.studypage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bit.studypage.dto.CommentDTO;
import com.bit.studypage.entity.BoardQna;
import com.bit.studypage.entity.Comment;
import com.bit.studypage.entity.Users;
import com.bit.studypage.repository.BoardQnaRepository;
import com.bit.studypage.repository.CommentRepository;
import com.bit.studypage.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardQnaRepository boardRepository;

    // 댓글 작성하기
    @Transactional
    public CommentDTO writeComment(long boardId, CommentDTO commentDto, Users user) {
        // 게시판 번호로 게시글 찾기
        BoardQna board = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없습니다.");
        });

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .user(user)
                .board(board)
                .build();

        commentRepository.save(comment);

        return CommentDTO.toDto(comment);
    }

    // 글에 해당하는 전체 댓글 불러오기
    @Transactional(readOnly = true)
    public List<CommentDTO> getComments(long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardBoardId(boardId);
        List<CommentDTO> commentDtos = new ArrayList<>();

        comments.forEach(s -> commentDtos.add(CommentDTO.toDto(s)));
        return commentDtos;
    }

    // 댓글 삭제하기
    @Transactional
    public String deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> {
            return new IllegalArgumentException("댓글 Id를 찾을 수 없습니다.");
        });
        commentRepository.deleteById(commentId);
        return "삭제 완료";
    }


}