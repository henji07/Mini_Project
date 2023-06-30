package com.bit.studypage.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j //로그 찍을 때 
@Getter
@ToString
@Entity
@Data
@Table(name="del_users")
public class DelUsers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="users_id")
	private Long usersId;

	@Column(name="user_id")
	private String userId;

	@Column(name="user_name")
	private String name;

	@Column(name="password")
	private String password;

	@Column(name="email")
	private String email;

	@Column(name="phone_number")
	private String phone;

	@Column(name="interest")
	private String interest;

	@Column(name="gender")
	private String gender;

	@Column(name="user_nickname")
	private String userNickname;

	@Column(name="is_terms")
	private int isTerms;

	//기본 생성자
	public DelUsers() {
		super();
	}
	//빌더
	//생성자 주입 - setter 만들면 안 되니까 data에 값을 넣는 것.
	//엔티티 값을 레파지토리에 던져줘야 하니까 값을 설정해준다.
	//새로 만들 때만 쓰는 거
	@Builder
	public DelUsers(Map<String, Object> data) {
		this.userId = (String)data.get("userId");
		this.name = (String)data.get("name");
		this.password = (String)data.get("password");
		this.email = (String)data.get("email");
		this.phone = (String)data.get("phone");
		this.interest = (String)data.get("interest");
		this.gender = (String)data.get("gender");
		this.userNickname = (String)data.get("userNickname");
	}
	@Builder
	public DelUsers(Users users) {
		this.userId = users.getUserId();
		this.name = users.getName();
		this.password = users.getPassword();
		this.email = users.getEmail();
		this.phone = users.getPhone();
		this.interest = users.getInterest();
		this.gender = users.getGender();
		this.userNickname = users.getUserNickname();
	}
}
