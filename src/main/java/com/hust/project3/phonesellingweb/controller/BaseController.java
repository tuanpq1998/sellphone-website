package com.hust.project3.phonesellingweb.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.hust.project3.phonesellingweb.model.Cart;

public class BaseController {
	@ModelAttribute("cart")
	public Cart initCart(HttpSession session) {
		Cart cart = (Cart)session.getAttribute("cart");
		if (cart == null)
			return new Cart();
		return cart;
	}
	
	public void resetCart(HttpSession session, Model model) {
		Cart cart = new Cart();
		session.setAttribute("cart", cart);
		model.addAttribute("cart", cart);
	}
	
	public boolean isCartEmpty(HttpSession session) {
		Cart cart = (Cart)session.getAttribute("cart");
		if (cart == null)
			return true;
		return cart.getProducts().size() == 0;
	}
	
	public boolean isLoggedIn(Principal principal) {
		return principal != null;
	}
}
