package mini.test.member.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mini.test.member.model.MemberForm;
import mini.test.member.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	//회원 가입 화면 열어보기 
	@GetMapping("/members/new")
	public String createForm(Model model) {
		model.addAttribute("memberForm", new MemberForm());
		return "view/createMemberForm";
	}
	
	@PostMapping("/members/regist")
	@ResponseBody //json으로 바꿈 
	public Map<String, Object> create(@RequestBody MemberForm form) {
		//@RequestBody json을 객체로 
		Map<String, Object> data = new HashMap<>();
		String result = "";
		
		try{
			result = memberService.join(form);
		}
		catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//json은 키, 밸류로 넘겨야 하기 때문에 Map으로 받아서 넘김 
		//화면에 json타입으로 던져줌 
		data.put("result", result);
		
		return data;
	}

}
