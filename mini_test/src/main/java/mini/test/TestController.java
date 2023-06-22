package mini.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	
	@GetMapping("test")
	public String hello(Model model) {
		model.addAttribute("data", "구만 노래 좋당");
		
		return "test";
	}
}
