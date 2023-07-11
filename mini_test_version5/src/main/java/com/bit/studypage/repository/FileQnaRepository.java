package com.bit.studypage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.studypage.entity.FileQna;

@Repository
public interface FileQnaRepository extends JpaRepository<FileQna, Long> {

	//boardId를 매개변수로 받아, 해당 boardId를 가진 FileQnaEntity 객체의 목록을 반환
	List<FileQna> findAllByBoardId(long boardId);


}