package com.hust.project3.phonesellingweb.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hust.project3.phonesellingweb.model.Cart;
import com.hust.project3.phonesellingweb.service.CartService;

@Controller
public class CartController extends BaseController {
	
	@Autowired
	private CartService cartService;
	
	@GetMapping({"/gio-hang/", "/gio-hang"})
	public String showCart(Model model, HttpSession session) {
		return "cart";
	}
	
	@PostMapping({"/gio-hang/", "/gio-hang"})
	public String updateCart(@ModelAttribute("cart") Cart tempCart, Model model, HttpSession session) {
		Cart sessionCart = (Cart)session.getAttribute("cart");
		cartService.update(sessionCart, tempCart);

		session.setAttribute("cart", sessionCart);
		model.addAttribute("cart", sessionCart);
		return "cart";
	}
	
}
