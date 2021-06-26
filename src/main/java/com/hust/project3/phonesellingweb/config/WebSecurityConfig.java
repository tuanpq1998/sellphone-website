package com.hust.project3.phonesellingweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hust.project3.phonesellingweb.service.UserService;
import com.hust.project3.phonesellingweb.utility.StringHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(provider);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.rememberMe().key(StringHandler.generateRandomString(10)).tokenValiditySeconds(1296000).userDetailsService(userService);
		http
			.authorizeRequests()
				.antMatchers("/thong-tin**", "/updatePhoneAndAddress**", "/don-hang**").authenticated()
				.antMatchers("/admin/**", "/upload/images/tmp/**").hasRole("ADMIN")
				.anyRequest().permitAll()
				.and().formLogin().loginPage("/dang-nhap")
				.loginProcessingUrl("/dang-nhap")
//				.successHandler(customLoginSuccessHandler)
				.permitAll()
				.and().logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/dang-xuat"))
					.logoutSuccessUrl("/dang-nhap?out").permitAll()
				.and().exceptionHandling().accessDeniedPage("/error")
				.and()
        .csrf().disable().cors();
	}
}
