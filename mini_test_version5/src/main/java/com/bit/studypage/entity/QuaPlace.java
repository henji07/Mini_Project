package com.bit.studypage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class QuaPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;
    private String brchCd;
    private String brchNm;
    private String examAreaGbNm;
    private String examAreaNm;
    private String plceLoctGid;
    private String telNo;
    double x;
    double y;
}
