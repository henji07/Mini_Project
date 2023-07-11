package com.bit.studypage.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bit.studypage.dto.BoardQnaDTO;
import com.bit.studypage.dto.FileQnaDTO;
import com.bit.studypage.entity.BoardQna;
import com.bit.studypage.entity.FileQnaEntity;
import com.bit.studypage.repository.BoardQnaRepository;
import com.bit.studypage.repository.CommentRepository;
import com.bit.studypage.repository.FileQnaRepository;
import com.bit.studypage.service.BoardQnaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor //생성자 주입
public class BoardQnaServiceImpl implements BoardQnaService {

    private final BoardQnaRepository boardRepository;
    private final FileQnaRepository fileRepository;
    private final CommentRepository commentRepository;

    @Value("${file.path}")
    private String fileUploadDir;

    //글 등록
    @Override
    public Map<String, Object> insertBoard(BoardQnaDTO boardDTO, List<MultipartFile> files) {

        // 임시로 파일 정보를 저장할 리스트와 결과를 저장할 맵 생성
        List<Map<String, Object>> tmpFileList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        long boardId = 0;

        try {
            // 파일이 있는 경우, 각 파일을 처리
            if(ObjectUtils.isNotEmpty(files)) {
                System.out.println(files.size());
                for(MultipartFile f : files) {
                    // 파일 정보를 저장할 맵 생성
                    Map<String, Object> data = new HashMap<>();

                    // 파일의 원래 이름에서 확장자를 추출하고, 새 파일 이름을 생성
                    String originalFileName = f.getOriginalFilename();

                    String ext = StringUtils.substring(originalFileName, StringUtils.lastIndexOf(originalFileName, "."));
                    String storeFileName = "qna/" + UUID.randomUUID().toString() + ext;
                    String fileType = f.getContentType();

                    //파일을 서버에 업로드하고, 파일 정보를 맵에 저장
                    File fle = new File(fileUploadDir + storeFileName);
                    f.transferTo(fle);

                    data.put("originalFileName", originalFileName);
                    data.put("fileType", fileType);
                    data.put("storeFileName", storeFileName);

                    // 임시 파일 정보 리스트에 추가
                    tmpFileList.add(data);
                }
            }
        } catch (Exception ex) {
            result.put("errorMessage", ex.getMessage());
            return result;
        }

        System.out.println(boardDTO.toString());

        // BoardQna 엔티티로 변환하고 저장
        BoardQna board = BoardQna.builder().data(boardDTO).build();

        // 보드 엔티티를 데이터베이스에 저장하고 결과를 반환
        BoardQna entity = boardRepository.save(board);

        // 저장된 엔티티에서 게시글 아이디를 가져옴
        if(ObjectUtils.isNotEmpty(entity)) {
            boardId = entity.getBoardId();

            // 파일 정보가 있는 경우, 각 파일 정보를 FileQnaEntity로 변환하고 저장
            if(ObjectUtils.isNotEmpty(tmpFileList)) {

                for(Map<String,Object> data: tmpFileList) {

                    FileQnaEntity fqEntity = FileQnaEntity.builder()
                            .originalFileName((String)data.get("originalFileName"))
                            .fileType((String)data.get("fileType"))
                            .storeFileName((String)data.get("storeFileName"))
                            .boardId(boardId).build();

                    fileRepository.save(fqEntity);

                }

            }

        }


        // 결과 맵에 게시글 아이디를 저장하고 반환
        result.put("boardId", boardId);
        return result;
    }

    //글 수정
    public Map<String, Object> updateBoard(BoardQnaDTO boardDTO, List<MultipartFile> files) {

        System.out.println("넘어온 정보=" + boardDTO.toString());
        BoardQnaDTO dto = null;
        //게시글 조회
        Optional<BoardQna> option = boardRepository.findById(boardDTO.getBoardId());


        // 추가 파일 정보를 저장할 리스트 생성
        List<Map<String, Object>> newFileList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        long boardId = 0;

        try {
            // 파일이 있는 경우, 각 파일을 처리
            if(ObjectUtils.isNotEmpty(files)) {
                System.out.println(files.size());
                for(MultipartFile f : files) {
                    // 파일 정보를 저장할 맵 생성
                    Map<String, Object> data = new HashMap<>();

                    // 파일의 원래 이름에서 확장자를 추출하고, 새 파일 이름을 생성
                    String originalFileName = f.getOriginalFilename();

                    String ext = StringUtils.substring(originalFileName, StringUtils.lastIndexOf(originalFileName, "."));
                    String storeFileName = "qna/" + UUID.randomUUID().toString() + ext;
                    String fileType = f.getContentType();

                    //파일을 서버에 업로드하고, 파일 정보를 맵에 저장
                    File fle = new File(fileUploadDir + storeFileName);
                    f.transferTo(fle);

                    data.put("originalFileName", originalFileName);
                    data.put("fileType", fileType);
                    data.put("storeFileName", storeFileName);

                    // 임시 파일 정보 리스트에 추가
                    newFileList.add(data);
                }
            }
        } catch (Exception ex) {
            result.put("errorMessage", ex.getMessage());
            return result;
        }

        System.out.println(boardDTO.toString());


        // BoardQna 엔티티로 변환하고 저장
        BoardQna board = BoardQna.builder().data(boardDTO).build();

        // 보드 엔티티를 데이터베이스에 저장하고 결과를 반환
        BoardQna entity = boardRepository.save(board);

        // 저장된 엔티티에서 게시글 아이디를 가져옴
        if(ObjectUtils.isNotEmpty(entity)) {
            boardId = entity.getBoardId();

            // 파일 정보가 있는 경우, 각 파일 정보를 FileQnaEntity로 변환하고 저장
            if(ObjectUtils.isNotEmpty(newFileList)) {

                for(Map<String,Object> data: newFileList) {

                    FileQnaEntity fqEntity = FileQnaEntity.builder()
                            .originalFileName((String)data.get("originalFileName"))
                            .fileType((String)data.get("fileType"))
                            .storeFileName((String)data.get("storeFileName"))
                            .boardId(boardId).build();

                    fileRepository.save(fqEntity);

                }

            }

            // 파일 삭제 처리
            for (Long fileId : boardDTO.getAttachDelete()) {
                fileRepository.deleteById(fileId);
            }

        }


        // 결과 맵에 게시글 아이디를 저장하고 반환
        result.put("boardId", boardId);
        return result;
    }


    //글 삭제
    public void deleteBoard(long boardId) {
        boardRepository.deleteById(boardId);
    }

    //글 상세 조회
    @Override
    public BoardQnaDTO getBoardDetail(long boardId) {
        // 결과를 저장할 DTO 생성
        BoardQnaDTO dto = new BoardQnaDTO();
        // 주어진 아이디로 게시글 엔티티를 찾음
        Optional<BoardQna> option = boardRepository.findById(boardId);

        if(ObjectUtils.isNotEmpty(option)) {
            BoardQna board = option.get();
            // 게시글 엔티티가 있는 경우, 조회수를 증가시키고 정보를 DTO에 복사
            if(ObjectUtils.isNotEmpty(board)) {
                board.updateReadCount(board.getBoardCnt() + 1); // 조회수 증가
                boardRepository.save(board); // 변경된 조회수 저장

                BeanUtils.copyProperties(board, dto);

                // 게시글에 해당하는 파일 엔티티를 찾음
                List<FileQnaEntity> fileEntityList = fileRepository.findAllByBoardId(boardId);
                List<FileQnaDTO> fileQnaDTOList = new ArrayList<>();

                // 파일 엔티티가 있는 경우, 각 파일 엔티티의 정보를 FileQnaDTO에 복사
                if(ObjectUtils.isNotEmpty(fileEntityList)) {
                    for(FileQnaEntity f : fileEntityList) {
                        FileQnaDTO fileDTO = new FileQnaDTO();
                        BeanUtils.copyProperties(f, fileDTO);
                        fileQnaDTOList.add(fileDTO);
                    }
                }
                // 파일 DTO 리스트가 있는 경우, 이를 결과 DTO에 설정
                if(ObjectUtils.isNotEmpty(fileQnaDTOList)) {
                    dto.setFileList(fileQnaDTOList);
                }
            }
        }
        return dto;
    }

    //글 목록
    @Override
    public List<BoardQnaDTO> getBoardList(int pageNum) {

        int pageSize = 5;  // 한 페이지에 보여질 게시글 수

        //Spring Data JPA의 Pageable 인터페이스와 Page 클래스를 사용해서 페이지네이션을 구현
        //PageRequest의 of 메소드를 사용해 페이지 번호와 페이지 크기를 입력하고, 정렬 조건을 지정
        //페이지 번호는 0부터 시작하므로 사용자가 입력하는 페이지 번호에서 1을 빼줌
        //boardRepository의 findAll 메소드에 pageable 객체를 전달해 원하는 페이지의 데이터를 가져온다.
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "boardId"));
        Page<BoardQna> pageInfo =  boardRepository.findAll(pageable);

        //이를 BoardDTO 객체 리스트로 변환한 후 반환
        List<BoardQna> boardList = pageInfo.getContent();

        List<BoardQnaDTO> dataList = new ArrayList<>();

        if(ObjectUtils.isNotEmpty(boardList)) {
            for(BoardQna b : boardList) {

                BoardQnaDTO dto = new BoardQnaDTO();
                BeanUtils.copyProperties(b, dto);

                // 댓글 수 조회
                int commentCount = commentRepository.countByBoard_BoardId(b.getBoardId());
                dto.setCommentCount(commentCount);

                dataList.add(dto);
            }
        }

        return dataList;
    }

    //전체 페이지 수 반환
    @Override
    public Object getTotalPages() {
        int pageSize = 5;
        long totalBoards = boardRepository.count();
        return (int) Math.ceil((double) totalBoards / pageSize);
    }

    //파일 정보 가져오기
    public FileQnaDTO inqurityFileInfo(long id) {

        FileQnaDTO dto = null;
        Optional<FileQnaEntity> optional = fileRepository.findById(id);
        FileQnaEntity entity = null;

        if(ObjectUtils.isNotEmpty(optional)) {
            entity = optional.get();
            dto = new FileQnaDTO();

            BeanUtils.copyProperties(entity, dto);
        }

        return dto;
    }



}
