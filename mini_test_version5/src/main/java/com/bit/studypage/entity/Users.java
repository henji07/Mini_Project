package com.bit.studypage.entity;


import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j //로그 찍을 때 
@Getter
@ToString
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name="users")
public class Users implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "interest")
    private String interest;

    @Column(name = "gender")
    private String gender;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "is_terms")
    private int isTerms;
    @ElementCollection(fetch = FetchType.EAGER) //엔티티 클래스 내의 컬렉션 필드를 매핑할 때, 관련 데이터를 즉시 로드하도록 지정하는 것(별도의 테이블로 관리)
    @Builder.Default //@Builder 어노테이션이 적용된 클래스의 빌더 패턴에서 기본값을 설정하는 데 사용
    private List<String> roles = new ArrayList<>();

    //기본 생성자
    public Users() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}