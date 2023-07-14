package com.bit.studypage.service;

import com.bit.studypage.entity.Qualification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QualificationService {
    void save(Qualification qualification);
    List<String> findKey(String key);
    String findJmfldnm(String jmf);
    Page<Qualification> getQuaList(Pageable pageable);
    Page<Qualification> getSearchQuaList(String jmf,Pageable pageable);
    void saveDate(String name, String date);
    String findName(String name);
}
