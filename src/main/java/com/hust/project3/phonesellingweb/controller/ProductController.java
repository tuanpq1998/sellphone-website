package com.hust.project3.phonesellingweb.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hust.project3.phonesellingweb.entity.Product;
import com.hust.project3.phonesellingweb.service.ProductService;

@Controller
@RequestMapping("/dtdd")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String getAllProduct() {
		return "index";
	} 
	
	@GetMapping("/{productId}")
	public String getProduct(@PathVariable int productId, Model model, HttpSession session, Principal principal) {

		Product product = productService.findById(productId);
		
		productService.increaseSeenNum(product.getId(), product.getSeenCount()+1);
		model.addAttribute("product", product);
		return "product";
	}	
	
	
}