package com.hust.project3.phonesellingweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hust.project3.phonesellingweb.service.UserService;

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
	
	
	@Configuration
	@Order(1)
	public static class AdminConfigurationAdapter extends WebSecurityConfigurerAdapter {
	    public AdminConfigurationAdapter() {
	        super();
	    }


	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.antMatcher("/admin*")
	          .authorizeRequests()
	          .anyRequest()
	          .hasRole("ADMIN")
	          
	          .and()
	          .formLogin()
	          .loginPage("/login_admin").usernameParameter("username").passwordParameter("password")
//	          .loginProcessingUrl("/admin/login_admin_processing_url")
	          .failureUrl("/login_admin?error=loginError")
	          .defaultSuccessUrl("/admin")
	          
	          .and()
	          .logout()
	          .logoutUrl("/admin_logout")
	          .logoutSuccessUrl("/protectedLinks")
	          .deleteCookies("JSESSIONID")
	          
	          .and()
	          .exceptionHandling()
	          .accessDeniedPage("/403")
	          
	          .and()
	          .csrf().disable();
	    }
	}
	
	@Configuration
	@Order(2)
	public static class UserConfigurationAdapter extends WebSecurityConfigurerAdapter {

	    public UserConfigurationAdapter() {
	        super();
	    }

	    protected void configure(HttpSecurity http) throws Exception {
	        http.antMatcher("/user*")
	          .authorizeRequests()
	          .anyRequest()
	          .hasRole("USER")
	          
	          .and()
	          .formLogin()
	          .loginPage("/dang-nhap").usernameParameter("username").passwordParameter("password")
	          .loginProcessingUrl("/dang-nhap")
	          .failureUrl("/dang-nhap?error")
//				.successHandler(customLoginSuccessHandler)
	          
	          .and()
	          .logout()
	          .logoutUrl("/dang-xuat")
	          .logoutSuccessUrl("/dang-nhap?dang-xuat")
	          .deleteCookies("JSESSIONID")
	          
	          .and()
	          .exceptionHandling()
	          .accessDeniedPage("/403")
	          
	          .and()
	          .csrf().disable();
	    }
	}
	
	@Configuration
    @Order(3)
    public static class GuestSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .anyRequest().permitAll();

        }

	}

}
