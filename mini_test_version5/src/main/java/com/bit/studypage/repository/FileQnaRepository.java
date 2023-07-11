package com.bit.studypage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.studypage.entity.FileQnaEntity;

@Repository
public interface FileQnaRepository extends JpaRepository<FileQnaEntity, Long> {

    List<FileQnaEntity> findAllByBoardId(long boardId);

}