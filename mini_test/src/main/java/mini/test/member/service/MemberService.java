package mini.test.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mini.test.common.jpa.entity.Member;
import mini.test.common.jpa.repository.MemberRepository;
import mini.test.member.model.MemberForm;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	//회원 가입
	public String join(MemberForm form) {
		
		//성공할 때만 success로 나머지는 fail 
		String result = "fail";
		
		//배열을 스트링으로 바꿔주는 
		//DB에 배열이 들어갈 수 없기 때문에 
		//배열 넣으려면 테이블 따로 만들어야 함 
		//중간에 쉼표 넣어서 DB에 넣어주는 거 
		StringBuffer sb = new StringBuffer();
		String[] arr = form.getInterest();
		
		for(String str : arr) {
			if(sb.length() > 0)sb.append(",");
			sb.append(str);
		} 
		
		//Map에 값을 설정해서 엔티티에 빌드로 넘기기 위해 
		Map<String, Object> formData = new HashMap<>();
		formData.put("userId", form.getUserId());
		formData.put("name", form.getName());
		formData.put("password", form.getPassword());
		formData.put("email", form.getEmail());
		formData.put("phone", form.getPhone());
		formData.put("interest", sb.toString());
		formData.put("gender", form.getGender());
		
		//Map으로 넘긴 데이터를 빌더를 통해 DB에 저장하는 방법 
		//Member.builder().data(formData).build() -> 엔티티를 만들어서 save로 레파지토리에 던짐
		//그 결과를 memberResult 받는다. 멤버 일련 번호가 있다. 
		Member memberResult = memberRepository.save(Member.builder().data(formData).build());
		
		//값이 0보다 크면 success
		if(ObjectUtils.isNotEmpty(memberResult)) {
			int memberSno = memberResult.getMemberSno();
			
			if(memberSno > 0) {
				// 등록 성공
				result = "success";
			}
		}
		
		return result;
	}
	
	
}
