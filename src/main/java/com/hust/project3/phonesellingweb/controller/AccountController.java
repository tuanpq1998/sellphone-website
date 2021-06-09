package com.hust.project3.phonesellingweb.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hust.project3.phonesellingweb.entity.Bill;
import com.hust.project3.phonesellingweb.entity.User;
import com.hust.project3.phonesellingweb.model.Cart;
import com.hust.project3.phonesellingweb.service.BillService;
import com.hust.project3.phonesellingweb.service.UserService;

@Controller
public class AccountController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BillService billService;
	
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
	
	@PostMapping({"/don-hang/", "/don-hang"})
	public String createBill(Principal principal, HttpSession session, Model model) {
		if (isCartEmpty(session))
			return "redirect:/gio-hang";
		Cart cart = (Cart)session.getAttribute("cart");
		User user = userService.findByUsername(principal.getName());
		Bill newBill = billService.createAndSave(user, cart);
		
		resetCart(session, model);
		model.addAttribute("bill", newBill);
		return "bill";
	}
	
	@GetMapping({"/thong-tin", "/thong-tin/"})
	public String showInfo(Principal principal, Model model) {
		if (!isLoggedIn(principal))
			return "redirect:/";
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		
		List<Bill> bills = billService.findTop5ByUserId(user.getId());
		model.addAttribute("bills", bills);
		
		return "account/info";
	}
}
