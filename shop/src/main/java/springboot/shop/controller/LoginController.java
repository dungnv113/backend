/**
 * 
 */
package springboot.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dung
 *
 */
@Controller
@RequestMapping("/dang-nhap")
public class LoginController {

	@GetMapping
	public String login() {
		return "client/login";
	}

	@PostMapping
	public String login(@RequestParam(name = "err", required = false) String error) {
		return "login";
	}
}
