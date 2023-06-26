package com.bit.studypage.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long users_id;

    @NotNull
    private String user_nickname;

    @NotNull
    private String user_id;

    @NotNull
    private String password;

    @NotNull
    private String user_name;

    @NotNull
    private String email;

    private String phone_number;

    private String gender;

    private Date birth;

    private String is_terms;

    @NotNull
    private Timestamp last_login;

    private Timestamp field;

    private String is_admin = "0";

    private String changed_pass = "0";

    private String temporary_pass;

    @NotNull
    private Timestamp signup;

    private String withdrawal_id;
}
