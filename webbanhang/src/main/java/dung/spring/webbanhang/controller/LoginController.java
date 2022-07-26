package dung.spring.webbanhang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dang-nhap")
public class LoginController {
	@GetMapping
	public String login() {
		return "login";
	}

	@PostMapping
	public String login(@RequestParam(name = "err", required = false) String error) {
		return "login";
	}
}
