package com.bit.studypage.repository;

import com.bit.studypage.entity.QualificationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationInfoRepository extends JpaRepository<QualificationInfo,Long> {
    QualificationInfo findByName(String name);
}
