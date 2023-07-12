package com.bit.studypage.entity;




import java.util.*;
import java.util.stream.Collectors;


import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Slf4j //로그 찍을 때 
@Getter
@ToString
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class Users implements UserDetails, OAuth2User {

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

    @Column(name = "email", unique = true)
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
    //엔티티 클래스 내의 컬렉션 필드를 매핑할 때,
    // 관련 데이터를 즉시 로드하도록 지정하는 것(별도의 테이블로 관리)
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default //@Builder 어노테이션이 적용된 클래스의 빌더 패턴에서 기본값을 설정하는 데 사용
    private List<String> roles = new ArrayList<>();

    //@Transient: 테이블의 컬럼으로는 생성되지 않고 객체에서만 사용가능한 멤버변수
    //소셜로그인 시 사용자 정보를 담아줄 Map 선언
    @Transient
    Map<String, Object> attributes;

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

    //소셜 로그인 정보를 리턴해주는 메소드
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }


}



