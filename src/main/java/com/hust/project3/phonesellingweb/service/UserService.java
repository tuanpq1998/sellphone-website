package com.hust.project3.phonesellingweb.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.UserRepository;
import com.hust.project3.phonesellingweb.entity.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		if (user == null) 
			throw new UsernameNotFoundException("Username " + username
				+ "not found!");
		return new org.springframework.security.core.userdetails.User(username, 
				user.getPassword(), new ArrayList<>());
	}

}
