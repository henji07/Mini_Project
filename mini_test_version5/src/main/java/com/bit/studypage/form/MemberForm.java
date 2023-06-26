package com.bit.studypage.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class MemberForm {
	
	private Long memberSno;
	
	private String userId;
	
	private String name;
	
	private String password;
	
	private String email;
	
	private String phone;
	
	private String[] interest;
	
	private String gender;
	
}
