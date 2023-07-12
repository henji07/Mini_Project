package com.bit.studypage.service.impl;

import com.bit.studypage.entity.QualificationInfo;
import com.bit.studypage.repository.QualificationInfoRepository;
import com.bit.studypage.service.QualificationInfoService;
import org.springframework.stereotype.Service;

@Service
public class QualificationInfoImpl implements QualificationInfoService {
    private QualificationInfoRepository qualificationInfoRepository;
    QualificationInfoImpl(QualificationInfoRepository qualificationInfoRepository){
        this.qualificationInfoRepository = qualificationInfoRepository;
    }
    @Override
    public QualificationInfo getInfo(String name) {
        return qualificationInfoRepository.findByName(name);
    }
}
