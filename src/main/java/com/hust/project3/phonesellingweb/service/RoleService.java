package com.hust.project3.phonesellingweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.RoleRepository;
import com.hust.project3.phonesellingweb.entity.Role;
import com.hust.project3.phonesellingweb.utility.ConstantVariable;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;
	
	public Role findRoleUser() {
		return repository.findByName(ConstantVariable.ROLE_USER_NAME);
	}
}
