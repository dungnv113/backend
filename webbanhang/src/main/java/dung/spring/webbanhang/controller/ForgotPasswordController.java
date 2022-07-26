package dung.spring.webbanhang.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dung.spring.webbanhang.entity.User;
import dung.spring.webbanhang.repository.UserRepo;
import dung.spring.webbanhang.service.MailService;

@Controller
@RequestMapping("/quen-matkhau")
public class ForgotPasswordController {
	@Autowired
	UserRepo userRepo;
	@Autowired
	MailService mailService;

	@GetMapping()
	public String forgotPassword() {
		return "forgotpassword";
	}

	@PostMapping("/forgot")
	public String forgotPassword(@RequestParam("username") String username, @RequestParam("email") String email,
			HttpSession session) {
		User user = userRepo.findByUsername(username);
		if (user != null && user.getEmail().equalsIgnoreCase(email)) {

			Random random = new Random();
			int ranNum = random.nextInt(100000) + 1;
			mailService.sendEmail(user.getEmail(), "OTP", String.valueOf(ranNum));
			session.setAttribute("OTP", ranNum);
			session.setAttribute("username", username);
			return "redirect:/otp";
		} else
			return "redirect:/quen-matkhau";
	}

}
