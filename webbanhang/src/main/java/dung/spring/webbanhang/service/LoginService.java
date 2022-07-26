package dung.spring.webbanhang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dung.spring.webbanhang.entity.User;
import dung.spring.webbanhang.repository.UserRepo;
@Service
public class LoginService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	private String username;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // khi login se tim user
																								// trong db
		User st = userRepo.findByUsername(username);

		if (st == null) {
			throw new UsernameNotFoundException("not found");
		}
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();

		for (String role : st.getRole()) {
			list.add(new SimpleGrantedAuthority(role)); // lấy role ở entity convert sang role security
		}
	

	// tao user cua security, user dang nhap hien tai
	org.springframework.security.core.userdetails.User currentUser = 
	new org.springframework.security.core.userdetails.User(username, st.getPassword(),list);
	
	return currentUser;
	}
}


