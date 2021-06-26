package com.hust.project3.phonesellingweb.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.hust.project3.phonesellingweb.service.UserService;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return !userService.isUsernameExisted(username);
	}

}
