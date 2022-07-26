package dung.spring.webbanhang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation") // bo canh bao
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true) // phan quyen tren ham,
																								// 3 kieu annotation
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService; // tim class co kieu userdetail
//xac thuc

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder()); // convert password
																									// sang encode
	}
//userdetailservice : lay thong tin user chuyen sang security

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/category/**").hasAnyRole("ADMIN", "SUBADMIN")// ROLE_
//				.hasAnyAuthority("ROLE_ADMIN")
//			.antMatchers("/ticket/**").authenticated()
//			.antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
				.anyRequest().permitAll().and().csrf().disable()
////			
				.formLogin().loginPage("/dang-nhap") // form dang nhap get
//				.loginProcessingUrl("/login") // action post
				.failureUrl("/dang-nhap?err=true").defaultSuccessUrl("/user/create", false).and().exceptionHandling()
				.accessDeniedPage("/login");
	}
}
