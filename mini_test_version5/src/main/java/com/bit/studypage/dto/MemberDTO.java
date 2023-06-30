package com.bit.studypage.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class MemberDTO {
	
	private Long memberSno;
	
	private String userId;
	
	private String name;
	
	private String password;

	private String email;
	
	private String phone;
	
	private String[] interest;
	
	private String gender;
	
}
