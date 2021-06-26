package com.hust.project3.phonesellingweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.config.CustomUserDetail;
import com.hust.project3.phonesellingweb.dao.UserRepository;
import com.hust.project3.phonesellingweb.entity.Role;
import com.hust.project3.phonesellingweb.entity.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleService roleService;
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		if (user == null) 
			throw new UsernameNotFoundException("Username " + username
				+ "not found!");
		return new CustomUserDetail(user);
	}

	public boolean isUsernameExisted(String username) {
		User user = findByUsername(username);
		return user!=null;
	}

	public void registerNew(User user) {
		Role role = roleService.findRoleUser();
		user.setRole(role);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

}
