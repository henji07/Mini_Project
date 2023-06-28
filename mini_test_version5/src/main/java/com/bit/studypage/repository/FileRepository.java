package com.bit.studypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.studypage.entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
