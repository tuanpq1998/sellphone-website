package com.hust.project3.phonesellingweb.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hust.project3.phonesellingweb.entity.User;
import com.hust.project3.phonesellingweb.service.UserService;

@Controller
public class AccountController extends BaseController {

	@Autowired
	private UserService userService;
	
	@GetMapping({"/dang-nhap/", "/dang-nhap"})
	public String showLogin(Principal principal) {
		if (isLoggedIn(principal))
			return "redirect:/";
		return "account/login";
	}
	
	@GetMapping({"/dang-ky/", "/dang-ky"})
	public String showRegister(Principal principal) {
		if (isLoggedIn(principal))
			return "redirect:/";
		return "account/signup";
	}
	
	@GetMapping({"/xac-thuc/", "/xac-thuc"})
	public String showAuthenRequest(Principal principal, Model model, HttpSession session) {
		if (isCartEmpty(session))
			return "redirect:/gio-hang";
		if (isLoggedIn(principal)) {
			User user = userService.findByUsername(principal.getName());
			model.addAttribute("user", user);
		}
		return "account/authen";
	}
	
	@PostMapping({"/xac-thuc/", "/xac-thuc"})
	public String createBill(Principal principal) {
		return null;
	}
}
