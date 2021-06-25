package com.hust.project3.phonesellingweb.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hust.project3.phonesellingweb.entity.product.Color;
import com.hust.project3.phonesellingweb.entity.product.Product;
import com.hust.project3.phonesellingweb.model.Cart;
import com.hust.project3.phonesellingweb.service.CartService;

@RestController
public class RestCartController {
	
	@Autowired
	private CartService cartService;

	@PostMapping("/addToCart")
	public ResponseEntity<?> addToCart( HttpSession session, Model model ,
			@RequestParam("productId") int productId, @RequestParam("quantity") int quantity,
			@RequestParam("colorId") int colorId) {

		Cart cart = (Cart)session.getAttribute("cart");
		if (cart == null)
			cart = new Cart();
		cartService.addProduct(cart, new Product(productId, quantity, new Color(colorId)));
		session.setAttribute("cart", cart);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	@PostMapping("/updatePhoneAndAddress")
	public ResponseEntity<?> changePhoneAndAdress(HttpSession session, Model model , 
			@RequestParam("address") String address, @RequestParam("phone") String phone) {
		Cart cart = (Cart)session.getAttribute("cart");
		if (cart == null)
			cart = new Cart();
		
		cartService.updatePhoneAndAddress(cart, address, phone);
		session.setAttribute("cart", cart);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	
}
