package com.bit.studypage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProfileImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;
    @Column
    private String originName;
    @Column
    private String imgName;
    @Column
    private String path;
}
