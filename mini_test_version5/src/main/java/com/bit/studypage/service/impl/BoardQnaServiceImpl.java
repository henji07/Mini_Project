package com.bit.studypage.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
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

import com.bit.studypage.dto.BoardCmmntQnaDTO;
import com.bit.studypage.dto.BoardQnaDTO;
import com.bit.studypage.dto.CommentQnaDTO;
import com.bit.studypage.dto.FileQnaDTO;
import com.bit.studypage.entity.BoardQna;
import com.bit.studypage.entity.CommentQna;
import com.bit.studypage.entity.FileQna;
import com.bit.studypage.repository.BoardQnaRepository;
import com.bit.studypage.repository.CommentQnaRepository;
import com.bit.studypage.repository.FileQnaRepository;
import com.bit.studypage.repository.LikeQnaRepository;
import com.bit.studypage.service.BoardQnaService;
import com.bit.studypage.service.dao.BoardQnaDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor //생성자 주입 
public class BoardQnaServiceImpl implements BoardQnaService {
	
	private final BoardQnaRepository boardRepository;
	private final FileQnaRepository fileRepository;
	private final CommentQnaRepository commentRepository;
	private final LikeQnaRepository likeRepository;
	
	private final BoardQnaDao boardQnaDao;
	
    @Value("${file.path}")
    private String fileUploadDir;
	
	/* 글 등록 */
	@Override
	public Map<String, Object> insertBoard(BoardQnaDTO boardDTO, List<MultipartFile> files ,String userId) {
		
		//임시로 파일 정보를 저장할 리스트 
		List<Map<String, Object>> tmpFileList = new ArrayList<>();
		//실행 결과를 저장할 맵 생성
		Map<String, Object> result = new HashMap<>();
		//boardId는 새로 추가할 게시물의 고유 아이디. boardId를 모르니까 임시 저장 
		long boardId = 0;
		
		try {
		// 파일이 있는 경우, 각 파일을 처리  
		if(ObjectUtils.isNotEmpty(files)) {
			
            System.out.println("파일사이즈"+files.size());
            
            	//files 리스트의 각 MultipartFile에 대하여 반복문 실행
            	for(MultipartFile f : files) {
            		//파일 null이면 안 들어가게 
            		if(!f.isEmpty()) {
            			//data 맵: 각 첨부 파일의 정보를 저장할 목적으로 생성
            		Map<String, Object> data = new HashMap<>();
            		
            		//원본 파일 이름, 파일 확장자, 저장할 파일 이름, 파일 타입 등의 정보를 추출, 저장될 새 이름 만들기
            		String originalFileName = f.getOriginalFilename();
                    String ext = StringUtils.substring(originalFileName, StringUtils.lastIndexOf(originalFileName, "."));
                    String storeFileName = "qna/" + UUID.randomUUID().toString() + ext;
                    String fileType = f.getContentType();
            		
                    //fle 객체는 첨부 파일을 서버에 업로드하기 위한 File 객체
                    //transferTo 메서드는 MultipartFile 객체에 저장된 파일을 실제 파일 시스템에 저장
            		File fle = new File(fileUploadDir + storeFileName);
            		f.transferTo(fle);
            		
            		//data 맵에 파일 정보를 저장
            		data.put("originalFileName", originalFileName);
            		data.put("fileType", fileType);
            		data.put("storeFileName", storeFileName);
            		
            		//tmpFileList에 data 맵 추가
            		tmpFileList.add(data);
            		}
            		
            	}
			}
		} catch (Exception ex) {
        	result.put("errorMessage", ex.getMessage());
        	return result;
        }
		
		System.out.println(boardDTO.toString());
		boardDTO.setBoardWriter(userId);
		
		//BoardQna 엔티티로 변환하고 저장 - 데이터베이스에 저장될 수 있는 형태
		BoardQna board = BoardQna.builder().data(boardDTO).build();
		
		// board 엔티티 객체를 데이터베이스에 저장하고, 그 결과를 entity 객체에 저장
		BoardQna entity = boardRepository.save(board);
		
		// entity 객체가 비어있지 않다면, 이 객체에서 게시글의 아이디를 가져와 boardId 변수에 저장
		if(ObjectUtils.isNotEmpty(entity)) {
			boardId = entity.getBoardId();
			
			//파일 정보가 있는 경우, 각 파일 정보를 FileQnaEntity로 변환하고 저장
			//tmpFileList가 비어있지 않다면, 이 리스트의 각 요소(맵 형태의 파일 데이터)에 대하여 반복문을 실행
			//조건문을 통해 첨부된 파일이 있는 경우에만 로직을 수행
			if(ObjectUtils.isNotEmpty(tmpFileList)) {
				
				for(Map<String,Object> data: tmpFileList) {
					//FileQnaEntity.builder()를 통해 각 파일 데이터를 FileQnaEntity 객체로 변환
					FileQna fqEntity = FileQna.builder()
	        				.originalFileName((String)data.get("originalFileName"))
	        				.fileType((String)data.get("fileType"))
	        				.storeFileName((String)data.get("storeFileName"))
	        				.boardId(boardId).build();
					
					fileRepository.save(fqEntity);
					
				}
				
			}
			
		} 
		
		
		//최종적으로 result 맵에 게시글의 아이디를 저장하고, result 맵을 반환
		result.put("boardId", boardId);
		return result;
	}

	/* 글 수정 */
	public Map<String, Object> updateBoard(BoardQnaDTO boardDTO, List<MultipartFile> files) {
		
		System.out.println("수정하려는 게시글 정보=" + boardDTO.toString());
		
		//반환할 맵 객체를 null로 초기화
		Map<String, Object> returnMap = null;
		
		//1. 수정할 게시글 조회 - 게시글 ID 사용해 저장소에서 해당 게시글 찾기
		Optional<BoardQna> option = boardRepository.findById(boardDTO.getBoardId());	
		System.out.println("수정할 게시글 조회= " + option);

		//찾은 게시글이 존재하는 경우
		if(ObjectUtils.isNotEmpty(option)) {
	        // Optional 객체에서 게시글 정보 가져오기
	        BoardQna board = option.get();
	        // 가져온 게시글이 존재하는 경우
	        if(ObjectUtils.isNotEmpty(board)) {
	            // 새로운 게시글 정보로 게시글 내용을 업데이트
	            board.updateContent(boardDTO);
	            
	    		//2. 삭제된 파일이 있으면 삭제
	   		 	for (Long fileId : boardDTO.getAttachDelete()) {
	            	fileRepository.deleteById(fileId);
	   		 	}
	   		 	
	   		 	// 변경된 내용을 저장소에 저장
	            BoardQna entity = boardRepository.save(board);
	   		 	
	   		 	//3. 새로 업로드된 파일이 있으면 저장
	            if(ObjectUtils.isNotEmpty(files)) {
	                for(MultipartFile f : files) {
	                    try {
	                        // 원본 파일 이름, 파일 확장자, 저장할 파일 이름, 파일 타입 등의 정보를 추출, 저장될 새 이름 만들기
	                        String originalFileName = f.getOriginalFilename();
	                        String ext = StringUtils.substring(originalFileName, StringUtils.lastIndexOf(originalFileName, "."));
	                        String storeFileName = "qna/" + UUID.randomUUID().toString() + ext;
	                        String fileType = f.getContentType();

	                        // fle 객체는 첨부 파일을 서버에 업로드하기 위한 File 객체
	                        // transferTo 메서드는 MultipartFile 객체에 저장된 파일을 실제 파일 시스템에 저장
	                        File fle = new File(fileUploadDir + storeFileName);
	                        f.transferTo(fle);

	                        // FileQnaEntity 객체 생성 및 설정
	                        FileQna fqEntity = FileQna.builder()
	                                .originalFileName(originalFileName)
	                                .fileType(fileType)
	                                .storeFileName(storeFileName)
	                                .boardId(entity.getBoardId()).build();  // 현재 수정하는 게시글의 id 설정

	                        // 파일 정보 저장
	                        fileRepository.save(fqEntity);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                        // Exception handling
	                    }
	                }
	            }
	   		 		            
	            // 저장한 게시글이 존재하는 경우
	            if(ObjectUtils.isNotEmpty(entity)) {
	                // 저장한 게시글의 정보를 담을 DTO 객체 생성
	                BoardQnaDTO dto = new BoardQnaDTO();
	                // 저장된 게시글의 정보를 DTO 객체로 복사
	                BeanUtils.copyProperties(entity, dto);
	                // DTO 객체를 맵으로 변환
	                returnMap = new HashMap<>();
	                // 맵에 게시글의 ID를 "boardId"라는 키로 저장
	                returnMap.put("boardId", dto.getBoardId());
	            }
	        }
	    }
		
	    // 최종적으로 생성된 맵 반환
	    return returnMap;
	}
        
	
	
	/* 글 삭제 */
	public void deleteBoard(long boardId) {
		boardRepository.deleteById(boardId);
	}
	
	/* 글 상세 조회 */
	@Override
	public BoardQnaDTO getBoardDetail(long boardId, long userId) {
		
		// 결과를 저장할 DTO 생성
		BoardQnaDTO dto = boardQnaDao.selectBoardQnaInfo(boardId);
		
		/*
		// 주어진 아이디로 게시글 엔티티를 찾음
		Optional<BoardQna> option = boardRepository.findById(boardId);
		
		if(ObjectUtils.isNotEmpty(option)) {
			BoardQna board = option.get();
			// 게시글 엔티티가 있는 경우, 조회수를 증가시키고 정보를 DTO에 복사
			if(ObjectUtils.isNotEmpty(board)) {
				board.updateReadCount(board.getBoardCnt() + 1); // 조회수 증가
				boardRepository.save(board); // 변경된 조회수 저장
				
				BeanUtils.copyProperties(board, dto);
		*/
		
		if(ObjectUtils.isNotEmpty(dto)) {
			// 게시글에 해당하는 파일 엔티티를 찾음
			List<FileQna> fileEntityList = fileRepository.findAllByBoardId(boardId);
			List<FileQnaDTO> fileQnaDTOList = new ArrayList<>();
			
			// 파일 엔티티가 있는 경우, 각 파일 엔티티의 정보를 FileQnaDTO에 복사
            if(ObjectUtils.isNotEmpty(fileEntityList)) {	            	
            	for(FileQna f : fileEntityList) {
            		FileQnaDTO fileDTO = new FileQnaDTO();
	                BeanUtils.copyProperties(f, fileDTO);
	                fileQnaDTOList.add(fileDTO);
            	}
            }
            // 파일 DTO 리스트가 있는 경우, 이를 결과 DTO에 설정
            if(ObjectUtils.isNotEmpty(fileQnaDTOList)) {
            	dto.setFileList(fileQnaDTOList);
            }
          //  System.out.println(boardId);
            List<BoardCmmntQnaDTO> commentList = boardQnaDao.selectCommentList(boardId);
            
            if(ObjectUtils.isNotEmpty(commentList)) {
            //	System.out.println(commentList.size());
            	dto.setComments(commentList);
            }
            
            if(userId > 0) {
            	dto.setHeart(likeRepository.existsByUserIdAndBoardId(userId, boardId));
            }
		}

	    return dto;
	}

	/* 글 목록 */ 
	@Override
	public List<BoardQnaDTO> getBoardList(int pageNum, String sortOption) {
		
		int pageSize = 8;  // 한 페이지에 보여질 게시글 수
		
		Sort sort; //옵션 객체 생성 
		
		// 정렬 옵션에 따라 Sort 객체를 생성
	    if ("recently".equals(sortOption)) {
	        sort = Sort.by(Sort.Direction.DESC, "boardId");
	    } else if ("view".equals(sortOption)) {
	        sort = Sort.by(Sort.Direction.DESC, "boardCnt");
	    } else if ("popular".equals(sortOption)) {
	        sort = Sort.by(Sort.Direction.DESC, "likeCount");
	    } else {
	        sort = Sort.by(Sort.Direction.DESC, "boardId");
	    }
		
		//Spring Data JPA의 Pageable 인터페이스와 Page 클래스를 사용해서 페이지네이션을 구현
		//PageRequest의 of 메소드를 사용해 페이지 번호와 페이지 크기를 입력하고, 정렬 조건을 지정
		//페이지 번호는 0부터 시작하므로 사용자가 입력하는 페이지 번호에서 1을 빼줌
		//boardRepository의 findAll 메소드에 pageable 객체를 전달해 원하는 페이지의 데이터를 가져온다.
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		Page<BoardQna> pageInfo =  boardRepository.findAll(pageable);
		
		//이를 BoardDTO 객체 리스트로 변환한 후 반환
		List<BoardQna> boardList = pageInfo.getContent();
		
		List<BoardQnaDTO> dataList = new ArrayList<>();
		
		if(ObjectUtils.isNotEmpty(boardList)) {
			for(BoardQna b : boardList) {
				
				BoardQnaDTO dto = new BoardQnaDTO();
				BeanUtils.copyProperties(b, dto);
				
				// 댓글 수 조회
	            int commentCount = commentRepository.countByBoardId(b.getBoardId());
	            dto.setCommentCount(commentCount);
				
				dataList.add(dto);
			}
		}
		
		return dataList;
	}

	/* 기존 게시물 전체 페이지 수 반환 */
	@Override
	public Object getTotalPages() {
		int pageSize = 8;
	    long totalBoards = boardRepository.count();
	    return (int) Math.ceil((double) totalBoards / pageSize);
	}
	
	/* 데이터 베이스에서 파일 정보 가져오기 */
	public FileQnaDTO inqurityFileInfo(long id) {
		
		FileQnaDTO dto = null;
		Optional<FileQna> optional = fileRepository.findById(id);
		FileQna entity = null;
		
		if(ObjectUtils.isNotEmpty(optional)) {
			entity = optional.get();
			dto = new FileQnaDTO();
			
			BeanUtils.copyProperties(entity, dto);
		}
		
		return dto;
	}
	
	/* 게시판 검색 기능 */
	public List<BoardQnaDTO> searchBoardsByTitle(String searchKeyword, int pageNum) {
		
		int pageSize = 5;  // 한 페이지에 보여질 게시글 수

	    // Pageable 인스턴스 생성
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "boardId"));

	    // 검색된 게시글 리스트를 페이징 처리하여 가져옴
	    Page<BoardQna> pageInfo = boardRepository.findByTitleContaining(searchKeyword, pageable);
	    
	    // BoardQna 리스트를 BoardQnaDTO 리스트로 변환
	    List<BoardQna> boardList = pageInfo.getContent();
        
        List<BoardQnaDTO> dataList = new ArrayList<>();

        if(ObjectUtils.isNotEmpty(boardList)) {
            for(BoardQna b : boardList) {

                BoardQnaDTO dto = new BoardQnaDTO();
                BeanUtils.copyProperties(b, dto);

                // 댓글 수 조회
                int commentCount = commentRepository.countByBoardId(b.getBoardId());
                dto.setCommentCount(commentCount);

                dataList.add(dto);
            }
        }

        return dataList;
    }
	
	/*검색된 게시물 기반으로 총 페이지 수 계산*/
	public int getSearchTotalPages(String keyword) {
	    int pageSize = 5;
	    long totalBoards = boardRepository.countByTitleContaining(keyword);
	    return (int) Math.ceil((double) totalBoards / pageSize);
	}

	
}
