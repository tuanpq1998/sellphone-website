package com.hust.project3.phonesellingweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/login_admin")
	public String showAdminLoginForm() {
		return "admin/login";
	}
}
