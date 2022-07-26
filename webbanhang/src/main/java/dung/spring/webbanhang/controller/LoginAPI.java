//package dung.spring.webbanhang.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import dung.spring.webbanhang.service.JwtTokenProvider;
//
//public class LoginAPI {
//	private AuthenticationManager authenticationManager;
//	@Autowired
//	private JwtTokenProvider jwtTokenProvider;
//
//	@PostMapping("/login")
//	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//
//			return jwtTokenProvider.createToken(username);
//		} catch (org.springframework.security.core.AuthenticationException e) {
//			throw new UsernameNotFoundException("Invalid username/password");
//		}
//	}
//}