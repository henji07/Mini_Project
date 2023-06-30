package com.bit.studypage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "users")
public class Users implements UserDetails { ////UserDetails는 시큐리티가 관리하는 객체이다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="users_id")
    private Long memberSno;

    @Column(name="user_id", nullable = false, unique = true)
    private String userId;

    @Column(name="user_name")
    private String name;

    @Column(name="password")
    private String password;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="phone_number")
    private String phone;

    @Column(name="interest")
    private String interest;

    @Column(name="gender")
    private String gender;

    @ElementCollection(fetch = FetchType.EAGER) //엔티티 클래스 내의 컬렉션 필드를 매핑할 때, 관련 데이터를 즉시 로드하도록 지정하는 것(별도의 테이블로 관리)
    @Builder.Default //@Builder 어노테이션이 적용된 클래스의 빌더 패턴에서 기본값을 설정하는 데 사용
    private List<String> roles = new ArrayList<>();

    private String picture;

    //빌더
    //생성자 주입 - setter 만들면 안 되니까 data에 값을 넣는 것.
    //엔티티 값을 레파지토리에 던져줘야 하니까 값을 설정해준다.
    //새로 만들 때만 쓰는 거

    public Users update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
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
