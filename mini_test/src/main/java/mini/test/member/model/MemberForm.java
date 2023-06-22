package mini.test.member.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class MemberForm {
	
	private int memberSno;
	
	@NotEmpty(message="아이디는 필수입니다.")
	private String userId;
	
	@NotEmpty(message="이름은 필수입니다.")
	private String name;
	
	@NotEmpty(message="비밀번호는 필수입니다.")
	private String password;
	
	@NotEmpty(message="이메일은 필수입니다.")
	private String email;
	
	private String phone;
	private String[] interest;
	private String gender;
	
}
