package dung.spring.webbanhang.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dung.spring.webbanhang.entity.User;
import dung.spring.webbanhang.repository.UserRepo;
import dung.spring.webbanhang.service.MailService;

@Controller
@RequestMapping("/otp")
public class ConfirmOTPController {
	@Autowired
	UserRepo userRepo;
	@Autowired
	MailService mailService;

	@GetMapping()
	public String forgotPassword() {
		return "ConfirmOTP";
	}

	@PostMapping
	public String forgotPassword(@RequestParam("otp") Integer otp, HttpSession session) {
		Integer doOTP = (Integer) session.getAttribute("OTP");
		System.err.println(doOTP);
		System.err.println(otp);

		if (otp != null && !"".equals(otp)) {
			if (doOTP.equals(otp)) {
				String username = session.getAttribute("username").toString();
				User user = userRepo.findByUsername(username);
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String generatedString = RandomStringUtils.randomAlphanumeric(8);
				user.setPassword(encoder.encode(generatedString));
				userRepo.save(user);
				
				mailService.sendEmail(user.getEmail(), "OTP", generatedString);
				return "redirect:/dang-nhap";

			}
		}
		return "/otp";
	}

}
