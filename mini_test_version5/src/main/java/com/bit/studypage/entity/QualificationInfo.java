package com.bit.studypage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class QualificationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name; //이름
    @Column(length = 500)
    private String info1; //개요
    private String info2; //수행직무
    private String info3; //실시기관명
    private String info4; //실시기관 홈페이지
    @Column(length = 1000)
    private String info5; //진로 및 전망
    @Column(length = 1000)
    private String info6; //출제 경향
}
