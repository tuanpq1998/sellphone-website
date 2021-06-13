package com.hust.project3.phonesellingweb.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hust.project3.phonesellingweb.entity.Product;
import com.hust.project3.phonesellingweb.service.ProductService;

@Controller
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/dtdd/{productSlug}.{productId}.html")
	public String getProduct(@PathVariable String productSlug, @PathVariable int productId, 
			Model model, HttpSession session, Principal principal) {
		
		Product product = productService.findById(productId);
		String rootProductSlug = product.getSlug();
		if (!rootProductSlug.equals(productSlug))
			return "redirect:/dtdd/" + rootProductSlug + "." + productId +".html";

		productService.increaseSeenNum(product.getId(), product.getSeenCount()+1);
		model.addAttribute("product", product);
		return "product";
	}	
	
	
}
