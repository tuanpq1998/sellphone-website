package com.hust.project3.phonesellingweb.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hust.project3.phonesellingweb.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

	public Role findByName(String roleUserName);

}
