package com.bit.studypage.repository;

import com.bit.studypage.entity.Qualification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    List<Qualification> findAllByJmfldnmContaining(String jmf);
    Qualification findByJmfldnm(String jmf);
    Page<Qualification> findAllByJmfldnmContaining(String jmf, Pageable pageable);
    //Page<Qualification> qualificationList(Pageable pageable);
}
