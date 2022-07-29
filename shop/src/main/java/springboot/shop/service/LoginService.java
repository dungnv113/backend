package springboot.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import springboot.shop.entity.User;
import springboot.shop.repository.UserRepo;

@Service
public class LoginService implements UserDetailsService {
	@Autowired
	UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		for (String role : user.getRole()) {
			list.add(new SimpleGrantedAuthority(role));

		}
		org.springframework.security.core.userdetails.User currentUser = new org.springframework.security.core.userdetails.User(
				username, user.getPassword(), list);
		return currentUser;
	}

}
