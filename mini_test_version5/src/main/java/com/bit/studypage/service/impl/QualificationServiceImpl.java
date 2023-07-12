package com.bit.studypage.service.impl;

import com.bit.studypage.entity.Qualification;
import com.bit.studypage.repository.QualificationRepository;
import com.bit.studypage.service.QualificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QualificationServiceImpl implements QualificationService {
    private QualificationRepository qualificationRepository;

    QualificationServiceImpl(QualificationRepository qualificationRepository) {
        this.qualificationRepository = qualificationRepository;
    }

    @Override
    public void save(Qualification qualification) {
        qualificationRepository.save(qualification);
    }

    @Override
    public List<String> findKey(String key) {
        List<Qualification> getKey = qualificationRepository.findAllByJmfldnmContaining(key);
        List<String> returnStr = new ArrayList<>();
        if (key.equals("")||key.equals(null)) {
            returnStr.add("");
        }else {
            for (Qualification a : getKey) {
                returnStr.add(a.getJmfldnm());
            }
        }
        return returnStr;
    }

    @Override
    public Page<Qualification> getQuaList(Pageable pageable) {
        return qualificationRepository.findAll(pageable);
    }

    @Override
    public Page<Qualification> getSearchQuaList(String jmf, Pageable pageable) {
        return qualificationRepository.findAllByJmfldnmContaining(jmf,pageable);
    }

    @Override
    public String findJmfldnm(String jmf) {
        System.out.println("여기가문제?");
        System.out.println(qualificationRepository.findByJmfldnm(jmf).toString());
        return qualificationRepository.findByJmfldnm(jmf).getJmcd();
    }
}
