package com.bit.studypage.entity;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j //로그 찍을 때 
@Getter
@ToString
@Entity
@Table(name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="users_id")
	private Long memberSno;
	
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
	
	//기본 생성자 
	public Users() {
		super();
	}
	
	//빌더 
	//생성자 주입 - setter 만들면 안 되니까 data에 값을 넣는 것. 
	//엔티티 값을 레파지토리에 던져줘야 하니까 값을 설정해준다. 
	//새로 만들 때만 쓰는 거 
	@Builder
	public Users(Map<String, Object> data) {
		this.userId = (String)data.get("userId");
		this.name = (String)data.get("name");
		this.password = (String)data.get("password");
		this.email = (String)data.get("email");
		this.phone = (String)data.get("phone");
		this.interest = (String)data.get("interest");
		this.gender = (String)data.get("gender");
	}
}
