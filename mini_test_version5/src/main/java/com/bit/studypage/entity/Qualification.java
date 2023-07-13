package com.bit.studypage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "jmcd")
    private String jmcd;

    @Column(name = "jmfldnm")
    private String jmfldnm;

    @Column(name = "mdobligfldcd")
    private String mdobligfldcd;

    @Column(name = "mdobligfldnm")
    private String mdobligfldnm;

    @Column(name = "obligfldcd")
    private String obligfldcd;

    @Column(name = "obligfldnm")
    private String obligfldnm;

    @Column(name = "qualgbcd")
    private String qualgbcd;

    @Column(name = "qualgbnm")
    private String qualgbnm;

    @Column(name = "seriescd")
    private String seriescd;

    @Column(name = "seriesnm")
    private String seriesnm;
    @Column(name = "date")
    private String date;
}

